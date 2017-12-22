package taiwan.no1.app.ssfm.features.search

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private val res: BaseEntity,
                                             private val addPlaylistItemCase: AddPlaylistItemCase,
                                             private val context: Context) : BaseViewModel() {
    val songName by lazy { ObservableField<String>() }
    val singerName by lazy { ObservableField<String>() }
    val coverUrl by lazy { ObservableField<String>() }
    val showBackground by lazy { ObservableBoolean() }
    val isPlaying by lazy { ObservableBoolean() }
    val glideCallback = glideListener<Bitmap> {
        onResourceReady = { _, _, _, _, _ ->
            showBackground.set(true)
            false
        }
    }
    var clickEvent: (track: BaseEntity) -> Unit = {}
    private val stateEventListener = { state: MusicPlayerState ->
        if (MusicPlayerState.Standby == state) isPlaying.set(false)
    }

    init {
        (res as MusicEntity.Music).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.url)
            songName.set(it.title)
            singerName.set(it.artist)
            coverUrl.set(it.coverURL)
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

    //region Action from View
    fun playOrStopMusicClick(view: View) {
        isPlaying.set(!isPlaying.get())
        (res as MusicEntity.Music).run {
            RxBus.get().post(VIEWMODEL_CHART_DETAIL_CLICK, url)
            MusicPlayerHelper.instance.run {
                play(url) {
                    lifecycleProvider.execute(addPlaylistItemCase,
                        AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlistId = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                            trackUri = url,
                            trackName = title,
                            artistName = artist,
                            coverUrl = coverURL,
                            lyricUrl = lyricURL,
                            duration = length))) { onNext { logw(it) } }
                    addStateChangedListeners(stateEventListener)
                }
            }
        }
    }

    fun optionClick(view: View) {
        clickEvent(res)
    }

    @Subscribe(tags = [(Tag(VIEWMODEL_CHART_DETAIL_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (res as MusicEntity.Music).url) isPlaying.set(false)
    }
    //endregion
}