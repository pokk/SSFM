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
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(private val searchMusicCase: SearchMusicV2Case,
                                           private val addPlaylistItemCase: AddPlaylistItemCase,
                                           private val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }
    val isPlaying by lazy { ObservableBoolean() }
    val stateEventListener = { state: MusicPlayerState ->
        if (MusicPlayerState.Standby == state) isPlaying.set(false)
    }
    var clickEvent: (track: BaseEntity) -> Unit = {}

    init {
        (item as TrackEntity.Track).let {
            trackName.set(it.name)
            trackNumber.set(it.attr?.rank ?: 0.toString())
            trackDuration.set(it.duration?.toInt()?.toTimeString())
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.realUrl)
        }
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

            lifecycleProvider.searchTheTopMusicAndPlayThenToPlaylist(searchMusicCase,
                                                                     addPlaylistItemCase,
                                                                     "$artistName $trackName",
                                                                     stateEventListener) {
                isPlaying.set(!isPlaying.get())
                realUrl = it.trackUri
                RxBus.get().post(RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK, it.trackUri)
            }
        }
    }

    fun trackOptionalOnClick(view: View) {
        clickEvent(item)
    }

    @Subscribe(tags = [(Tag(RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as TrackEntity.Track).realUrl) isPlaying.set(false)
    }
}