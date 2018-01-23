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
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playMusic
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(private val addPlaylistItemCase: AddPlaylistItemCase,
                                           private var item: PlaylistItemEntity,
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

    fun setTrackItem(item: PlaylistItemEntity, index: Int) {
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

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartAlbumDetailFragment.addToPlaylist]
     */
    fun trackOnClick(view: View) {
        lifecycleProvider.playMusic(addPlaylistItemCase, item, index)
    }

    fun trackOptionalOnClick(view: View) {
        clickEvent(item)
    }

    @Subscribe(tags = [(Tag(RxBusTag.VIEWMODEL_TRACK_CLICK))])
    fun changeToStopIcon(uri: String) = isPlaying.set(uri == item.trackUri)

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
        item.let {
            isPlaying.set(playerHelper.isCurrentUri(it.trackUri) && playerHelper.isPlaying)
            trackName.set(it.trackName)
            trackNumber.set(index.toString())
            trackDuration.set(it.duration.toTimeString())
        }
    }
}