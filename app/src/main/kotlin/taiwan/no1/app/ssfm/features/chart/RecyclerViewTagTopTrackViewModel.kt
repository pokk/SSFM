package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.devrapid.kotlinknifer.glideListener
import com.devrapid.kotlinknifer.palette
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_LONG_CLICK
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.searchTheTopMusicAndPlayThenToPlaylist
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewTagTopTrackViewModel(private val searchMusicCase: SearchMusicV2Case,
                                       private val addPlaylistItemCase: AddPlaylistItemCase,
                                       private var item: BaseEntity,
                                       private var index: Int) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val trackName by lazy { ObservableField<String>() }
    val ranking by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val textBackground by lazy { ObservableInt() }
    val textColor by lazy { ObservableInt() }
    val isPlaying by lazy { ObservableBoolean() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                textBackground.set(it.vibrantSwatch?.rgb ?: Color.BLACK)
                textColor.set(it.vibrantSwatch?.bodyTextColor ?: Color.GRAY)
            }
            false
        }
    }
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

    @Subscribe(tags = [(Tag(VIEWMODEL_TRACK_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as TrackEntity.Track).realUrl.orEmpty()) isPlaying.set(false)
    }

    @Subscribe(tags = [Tag(VIEWMODEL_TRACK_CLICK)])
    fun notifyClickIndex(index: Integer) {
        clickedIndex = index.toInt()
    }

    fun itemOnClick(view: View) {
        (item as TrackEntity.Track).run track@ {
            val trackName = name.orEmpty()
            val artistName = artist?.name.orEmpty()

            RxBus.get().post(VIEWMODEL_TRACK_CLICK, index)
            // Search the music first.
            lifecycleProvider.searchTheTopMusicAndPlayThenToPlaylist(searchMusicCase,
                                                                     addPlaylistItemCase,
                                                                     "$artistName $trackName") {
                realUrl = it.trackUri
                RxBus.get().post(VIEWMODEL_TRACK_CLICK, it.trackUri)
            }
        }
    }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.openBottomSheet]
     */
    fun trackOptionalOnClick(view: View) {
        RxBus.get().post(VIEWMODEL_TRACK_LONG_CLICK, item)
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
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.realUrl.orEmpty() && MusicPlayerHelper.instance.isPlaying())
            artistName.set(it.artist?.name)
            trackName.set(it.name)
            ranking.set(index.toString())
            duration.set(it.duration?.toInt()?.toTimeString())
            thumbnail.set(it.images?.get(LARGE)?.text.orEmpty())
        }
    }
}