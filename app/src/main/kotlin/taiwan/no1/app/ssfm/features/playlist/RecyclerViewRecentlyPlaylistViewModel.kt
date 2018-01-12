package taiwan.no1.app.ssfm.features.playlist

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
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewRecentlyPlaylistViewModel(private var item: BaseEntity,
                                            private var index: Int) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val isPlaying by lazy { ObservableBoolean() }
    private var clickedIndex = -1

    init {
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

    fun setPlaylistItemAndRefresh(item: BaseEntity, index: Int) {
        this.item = item
        this.index = index
        refreshView()
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        (item as PlaylistItemEntity).let {
            RxBus.get().post(VIEWMODEL_TRACK_CLICK, index)
            RxBus.get().post(VIEWMODEL_TRACK_CLICK, it.trackUri)
            MusicPlayerHelper.instance.apply { play(it.trackUri) }
        }
    }

    @Subscribe(tags = [Tag(VIEWMODEL_TRACK_CLICK)])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as PlaylistItemEntity).trackUri) isPlaying.set(false)
    }

    @Subscribe(tags = [Tag(VIEWMODEL_TRACK_CLICK)])
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
        (item as PlaylistItemEntity).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.trackUri && MusicPlayerHelper.instance.isPlaying())
            trackName.set(it.trackName)
            artistName.set(it.artistName)
            trackDuration.set(it.duration.toTimeString())
        }
    }
}