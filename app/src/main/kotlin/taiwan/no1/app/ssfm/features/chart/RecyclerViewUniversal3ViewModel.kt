package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.graphics.Bitmap
import android.graphics.Color
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 *
 * @author  jieyi
 * @since   10/26/17
 */
class RecyclerViewUniversal3ViewModel(private val searchMusicCase: SearchMusicV2Case,
                                      private val addPlaylistItemCase: AddPlaylistItemCase,
                                      private val item: BaseEntity) : BaseViewModel() {
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
    var clickEvent: (track: BaseEntity) -> Unit = {}
    val stateEventListener = { state: MusicPlayerState ->
        if (MusicPlayerState.Standby == state) isPlaying.set(false)
    }

    init {
        (item as TrackEntity.Track).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.realUrl.orEmpty())
            artistName.set(it.artist?.name)
            trackName.set(it.name)
            // FIXME(jieyi): 12/13/17 The attributes rank will be 1~10.
            ranking.set(it.attr?.rank.orEmpty())
            duration.set(it.duration?.toInt()?.toTimeString())
            thumbnail.set(item.images?.get(LARGE)?.text.orEmpty())
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

    @Subscribe(tags = [(Tag(VIEWMODEL_CHART_DETAIL_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as TrackEntity.Track).realUrl.orEmpty()) isPlaying.set(false)
    }

    fun itemOnClick(view: View) {
        (item as TrackEntity.Track).run track@ {
            val trackName = name.orEmpty()
            val artistName = artist?.name.orEmpty()
            // Search the music first.
            lifecycleProvider.execute(searchMusicCase, SearchMusicUsecase.RequestValue("$artistName $trackName")) {
                onNext {
                    // Pickup the first result, because it's the most correct.
                    it.data.items.first().run {
                        isPlaying.set(!isPlaying.get())
                        this@track.realUrl = url
                        RxBus.get().post(VIEWMODEL_CHART_DETAIL_CLICK, url)
                        MusicPlayerHelper.instance.run {
                            play(url) {
                                // Add this history to database.
                                lifecycleProvider.execute(addPlaylistItemCase,
                                    AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlistId = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                                        trackUri = url,
                                        trackName = title,
                                        artistName = artist,
                                        coverUrl = coverURL,
                                        lyricUrl = lyricURL,
                                        duration = length))) { onNext { logw(it) } }
                            }
                            addStateChangedListeners(stateEventListener)
                        }
                    }
                }
            }
        }
    }

    fun trackOptionalOnClick(view: View) {
        clickEvent(item)
    }
}