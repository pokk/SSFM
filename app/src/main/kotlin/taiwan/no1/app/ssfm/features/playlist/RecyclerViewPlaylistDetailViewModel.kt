package taiwan.no1.app.ssfm.features.playlist

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.devrapid.kotlinknifer.glideListener
import com.devrapid.kotlinknifer.palette
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.MUSICPLAYER_STATE_CHANGED
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_TRACK_CLICK
import taiwan.no1.app.ssfm.misc.extension.changeState
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.playThenToPlaylist
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState
import java.util.Date

/**
 *
 * @author  jieyi
 * @since   11/14/17
 */
class RecyclerViewPlaylistDetailViewModel(private val addPlaylistItemCase: AddPlaylistItemCase,
                                          private var item: BaseEntity,
                                          private var index: Int) : BaseViewModel() {
    val rank by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val trackName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val layoutBackground by lazy { ObservableField<Drawable>() }
    val showBackground by lazy { ObservableBoolean() }
    val isPlaying by lazy { ObservableBoolean() }
    val glideCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            showBackground.set(true)
            resource.palette(24).let {
                val start = gAlphaIntColor(it.vibrantSwatch?.rgb ?: gColor(R.color.colorSimilarPrimaryDark), 0.45f)
                val darkColor = gAlphaIntColor(it.darkVibrantSwatch?.rgb ?: gColor(R.color.colorPrimaryDark), 0.45f)
                val background = GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(start, darkColor))

                layoutBackground.set(background)
            }
            false
        }
    }
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
        RxBus.get().unregister(this)
    }
    //endregion

    fun setPlaylistItem(item: BaseEntity, index: Int) {
        this.item = item
        this.index = index
        refreshView()
    }

    fun trackOnClick(view: View) {
        (item as PlaylistItemEntity).let {
            // Copy a same object. There are some variables need to modify only.
            val playlistEntity = it.copy(id = 0,
                                         playlistId = Constant.DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                                         timestamp = Date())
            RxBus.get().post(VIEWMODEL_TRACK_CLICK, index)
            lifecycleProvider.playThenToPlaylist(addPlaylistItemCase, playlistEntity) {
                RxBus.get().post(VIEWMODEL_TRACK_CLICK, playlistEntity.trackUri)
            }
        }
    }

    @Subscribe(tags = [(Tag(VIEWMODEL_TRACK_CLICK))])
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
    @Subscribe(tags = [(Tag(MUSICPLAYER_STATE_CHANGED))])
    fun playerStateChanged(state: MusicPlayerState) {
        isPlaying.changeState(state, index, clickedIndex)
    }

    private fun refreshView() {
        (item as PlaylistItemEntity).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.trackUri && MusicPlayerHelper.instance.isPlaying())
            rank.set(index.toString())
            artistName.set(it.artistName)
            trackName.set(it.trackName)
            duration.set(it.duration.toTimeString())
            thumbnail.set(it.coverUrl)
        }
    }
}