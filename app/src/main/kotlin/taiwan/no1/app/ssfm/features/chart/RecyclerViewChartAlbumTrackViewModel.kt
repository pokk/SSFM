package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.searchTheTopMusicAndPlayThenToPlaylist
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(private val searchMusicCase: SearchMusicV2Case,
                                           private val addPlaylistItemCase: AddPlaylistItemCase,
                                           private var item: BaseEntity,
                                           private var index: Int) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }
    val isPlaying by lazy { ObservableBoolean() }
    var clickEvent: (track: BaseEntity) -> Unit = {}
    private var clickedIndex = -1

    init {
        refreshView()
    }

    fun setTrackItem(item: BaseEntity, index: Int) {
        this.item = item
        this.index = index
        refreshView()
    }

    //region Lifecycle
    override fun <E> onAttach(lifecycleProvider: LifecycleProvider<E>) {
        super.onAttach(lifecycleProvider)
        RxBus.get().register(this)
    }

    override fun onDetach() {
        super.onDetach()
        RxBus.get().unregister(this)
    }
    //endregion

    fun trackOnClick(view: View) {
        (item as TrackEntity.Track).run track@ {
            val trackName = name.orEmpty()
            val artistName = artist?.name.orEmpty()

            RxBus.get().post(VIEWMODEL_TRACK_CLICK, index)
            lifecycleProvider.searchTheTopMusicAndPlayThenToPlaylist(searchMusicCase,
                                                                     addPlaylistItemCase,
                                                                     "$artistName $trackName") {
                realUrl = it.trackUri
                RxBus.get().post(RxBusTag.VIEWMODEL_TRACK_CLICK, it.trackUri)
            }
        }
    }

    fun trackOptionalOnClick(view: View) {
        clickEvent(item)
    }

    @Subscribe(tags = [(Tag(RxBusTag.VIEWMODEL_TRACK_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as TrackEntity.Track).realUrl) isPlaying.set(false)
    }

    @Subscribe(tags = [Tag(RxBusTag.VIEWMODEL_TRACK_CLICK)])
    fun notifyClickIndex(index: Integer) {
        clickedIndex = index.toInt()
    }

    /**
     * @param state
     *
     * @event_from [MusicPlayerHelper.setPlayerListener]
     */
    @Subscribe(tags = [(Tag(RxBusTag.MUSICPLAYER_STATE_CHANGED))])
    fun playerStateChanged(state: MusicPlayerState) = isPlaying.changeState(state, index, clickedIndex)

    private fun refreshView() {
        (item as TrackEntity.Track).let {
            isPlaying.set(MusicPlayerHelper.instance.isCurrentUri(it.realUrl.orEmpty()) && MusicPlayerHelper.instance.isPlaying)
            trackName.set(it.name)
            trackNumber.set(it.attr?.rank ?: 0.toString())
            trackDuration.set(it.duration?.toInt()?.toTimeString())
        }
    }
}