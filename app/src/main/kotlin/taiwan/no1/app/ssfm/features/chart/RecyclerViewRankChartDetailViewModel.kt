package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.devrapid.kotlinknifer.glideListener
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.palette
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK_INDEX
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_LONG_CLICK_RANK_CHART
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.misc.utilies.devices.playThenToPlaylist
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Play
import weian.cheng.mediaplayerwithexoplayer.MusicPlayerState.Standby

/**
 * @author  jieyi
 * @since   11/24/17
 */
class RecyclerViewRankChartDetailViewModel(private val addPlaylistItemCase: AddPlaylistItemCase,
                                           private var item: BaseEntity,
                                           private var index: Int) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }
    val trackIndex by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val trackCover by lazy { ObservableField<String>() }
    val isPlaying by lazy { ObservableBoolean(false) }
    val layoutBackground by lazy { ObservableField<Drawable>() }
    val imageCallback = glideListener<Bitmap> {
        onResourceReady = { resource, _, _, _, _ ->
            resource.palette(24).let {
                val start = gAlphaIntColor(it.vibrantSwatch?.rgb ?: gColor(R.color.colorSimilarPrimaryDark), 0.65f)
                val darkColor = gAlphaIntColor(it.darkVibrantSwatch?.rgb ?: gColor(R.color.colorPrimaryDark), 0.65f)
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
        super.onDetach()
        RxBus.get().unregister(this)
    }
    //endregion

    fun setMusicItem(item: BaseEntity, index: Int) {
        this.item = item
        this.index = index
        refreshView()
    }

    fun trackOnClick(view: View) {
        RxBus.get().post(VIEWMODEL_CHART_DETAIL_CLICK, index)
        val playlistEntity = (item as MusicRankEntity.Song).run {
            PlaylistItemEntity(playlistId = Constant.DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                               trackUri = url,
                               trackName = title,
                               artistName = artist,
                               coverUrl = coverURL,
                               lyricUrl = lyricURL,
                               duration = length)
        }

        lifecycleProvider.playThenToPlaylist(addPlaylistItemCase, playlistEntity) {
            isPlaying.set(!isPlaying.get())
            RxBus.get().post(VIEWMODEL_CHART_DETAIL_CLICK, playlistEntity.trackUri)
        }
    }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.openBottomSheet]
     */
    fun trackOnLongClick(view: View): Boolean {
        RxBus.get().post(VIEWMODEL_LONG_CLICK_RANK_CHART, item)
        return true
    }

    @Subscribe(tags = [Tag(VIEWMODEL_CHART_DETAIL_CLICK)])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as MusicRankEntity.Song).url) isPlaying.set(false)
    }

    @Subscribe(tags = [Tag(VIEWMODEL_CHART_DETAIL_CLICK_INDEX)])
    fun notifyClickIndex(index: Int) {
        logw(index)
        clickedIndex = index
    }

    /**
     * @param state
     *
     * @event_from [MusicPlayerHelper.setPlayerListener]
     */
    @Subscribe(tags = [(Tag(RxBusTag.MUSICPLAYER_STATE_CHANGED))])
    fun playerStateChanged(state: MusicPlayerState) {
        logw(state, "???????????????", index)
        when (state) {
            Standby -> if (index != clickedIndex) isPlaying.set(false)
            Play -> if (index == clickedIndex) isPlaying.set(true)
            else -> null
        }
    }

    private fun refreshView() {
        (item as MusicRankEntity.Song).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.url && MusicPlayerHelper.instance.isPlaying())
            trackName.set(it.title)
            trackDuration.set(it.length.toTimeString())
            trackIndex.set(index.toString())
            artistName.set(it.artist)
            trackCover.set(it.coverURL)
        }
    }
}