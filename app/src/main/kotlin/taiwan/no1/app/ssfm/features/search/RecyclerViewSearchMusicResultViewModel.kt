package taiwan.no1.app.ssfm.features.search

import android.content.Context
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.view.View
import com.devrapid.kotlinknifer.glideListener
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.HELPER_ADD_TO_PLAYLIST
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playThenToPlaylist
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private var res: BaseEntity,
                                             private val addPlaylistItemCase: AddPlaylistItemCase,
                                             private val context: Context,
                                             private var index: Int) : BaseViewModel() {
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
    private var clickedIndex = -1

    init {
        refreshView()
    }

    fun setSearchResItem(item: BaseEntity, index: Int) {
        this.res = item
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

    //region Action from View
    fun playOrStopMusicClick(view: View) {
        val playlistEntity = (res as MusicEntity.Music).run {
            PlaylistItemEntity(playlistId = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                               trackUri = url,
                               trackName = title,
                               artistName = artist,
                               coverUrl = coverURL,
                               lyricUrl = lyricURL,
                               duration = length)
        }
        RxBus.get().post(VIEWMODEL_TRACK_CLICK, index)
        lifecycleProvider.playThenToPlaylist(addPlaylistItemCase, playlistEntity) {
            RxBus.get().post(VIEWMODEL_TRACK_CLICK, (res as MusicEntity.Music).url)
        }
        RxBus.get().post(HELPER_ADD_TO_PLAYLIST, playlistEntity.trackUri)
    }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchActivity.openBottomSheet]
     */
    fun optionClick(view: View) = RxBus.get().post(RxBusTag.VIEWMODEL_TRACK_LONG_CLICK, res)

    @Subscribe(tags = [(Tag(VIEWMODEL_TRACK_CLICK))])
    fun changeToStopIcon(uri: String) {
        if (uri != (res as MusicEntity.Music).url) isPlaying.set(false)
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
    //endregion

    private fun refreshView() {
        (res as MusicEntity.Music).let {
            isPlaying.set(playerHelper.isCurrentUri(it.url) && playerHelper.isPlaying)
            songName.set(it.title)
            singerName.set(it.artist)
            coverUrl.set(it.coverURL)
        }
    }
}