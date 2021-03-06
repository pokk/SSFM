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
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playMusic
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchMusicResultViewModel(private var res: PlaylistItemEntity,
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

    fun setSearchResItem(item: PlaylistItemEntity, index: Int) {
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
    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchResultFragment.addToPlaylist]
     */
    fun playOrStopMusicClick(view: View) {
        val playlistEntity = res.copy(playlistId = DATABASE_PLAYLIST_HISTORY_ID.toLong())

        lifecycleProvider.playMusic(addPlaylistItemCase, playlistEntity, index)
    }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchActivity.openBottomSheet]
     */
    fun optionClick(view: View) = RxBus.get().post(RxBusTag.VIEWMODEL_TRACK_LONG_CLICK, res)

    @Subscribe(tags = [(Tag(VIEWMODEL_TRACK_CLICK))])
    fun changeToStopIcon(uri: String) = isPlaying.set(uri == res.trackUri)

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
        res.let {
            isPlaying.set(playerHelper.isCurrentUri(it.trackUri) && playerHelper.isPlaying)
            songName.set(it.trackName)
            singerName.set(it.artistName)
            coverUrl.set(it.coverUrl)
        }
    }
}