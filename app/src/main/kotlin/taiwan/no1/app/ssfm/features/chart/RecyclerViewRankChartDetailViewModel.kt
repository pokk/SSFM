package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.toTimeString
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CHART_DETAIL_CLICK
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase

/**
 * @author  jieyi
 * @since   11/24/17
 */
class RecyclerViewRankChartDetailViewModel(private val addPlaylistItemCase: AddPlaylistItemCase,
                                           private val item: BaseEntity) : BaseViewModel() {
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
    var longClickEvent: (track: BaseEntity) -> Unit = {}

    init {
        (item as MusicRankEntity.Song).let {
            isPlaying.set(MusicPlayerHelper.instance.getCurrentUri() == it.url)
            trackName.set(it.title)
            trackDuration.set(it.length.toTimeString())
            trackIndex.set(1.toString())
            artistName.set(it.artist)
            trackCover.set(it.coverURL)
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
        isPlaying.set(!isPlaying.get())
        (item as MusicRankEntity.Song).run {
            RxBus.get().post(VIEWMODEL_CHART_DETAIL_CLICK, url)
            MusicPlayerHelper.instance.play(url) {
                lifecycleProvider.execute(addPlaylistItemCase,
                    AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlistId = Constant.DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                        trackUri = url,
                        trackName = title,
                        artistName = artist,
                        coverUrl = coverURL,
                        lyricUrl = lyricURL,
                        duration = length))) { onNext { logw(it) } }
            }
        }
    }

    fun trackOnLongClick(view: View): Boolean {
        longClickEvent.invoke(item)
        return true
    }

    @Subscribe(tags = [Tag(VIEWMODEL_CHART_DETAIL_CLICK)])
    fun changeToStopIcon(uri: String) {
        if (uri != (item as MusicRankEntity.Song).url) isPlaying.set(false)
    }
}