package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.HELPER_ADD_TO_PLAYLIST
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_LONG_CLICK
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.searchTheTopMusicAndPlayThenToPlaylist
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 *
 * @author  jieyi
 * @since   10/29/17
 */
class RecyclerViewChartArtistHotTrackViewModel(private val searchMusicCase: SearchMusicV2Case,
                                               private val addPlaylistItemCase: AddPlaylistItemCase,
                                               private var item: PlaylistItemEntity,
                                               private var index: Int) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }
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

    fun setTrackItem(item: PlaylistItemEntity, index: Int) {
        this.item = item
        this.index = index
        refreshView()
    }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartArtistDetailFragment.addToPlaylist]
     */
    fun trackOnClick(view: View) {
        item.run track@ {
            // Change the viewmodel state and view icon.
            RxBus.get().post(VIEWMODEL_TRACK_CLICK, index)
            // Search the music first.
            lifecycleProvider.searchTheTopMusicAndPlayThenToPlaylist(searchMusicCase,
                                                                     addPlaylistItemCase,
                                                                     "$artistName $trackName") {
                trackUri = it.trackUri
                RxBus.get().post(VIEWMODEL_TRACK_CLICK, it.trackUri)
            }
        }
        RxBus.get().post(HELPER_ADD_TO_PLAYLIST, item)
    }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.openBottomSheet]
     */
    fun trackOptionalOnClick(view: View) {
        RxBus.get().post(VIEWMODEL_TRACK_LONG_CLICK, item)
    }

    @Subscribe(tags = [Tag(VIEWMODEL_TRACK_CLICK)])
    fun changeToStopIcon(uri: String) = isPlaying.set(uri == item.trackUri)

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
        item.also {
            isPlaying.set(playerHelper.isCurrentUri(it.trackUri) && playerHelper.isPlaying)
            trackName.set(it.trackName)
            trackNumber.set(index.toString())
        }
    }
}