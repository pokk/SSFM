package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.gAlphaIntColor
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.extension.glideListener
import taiwan.no1.app.ssfm.misc.extension.palette
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayer
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
    val isplaying by lazy { ObservableBoolean(false) }
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

    init {
        (item as MusicRankEntity.Song).let {
            trackName.set(it.title)
            trackDuration.set(it.length.toTimeString())
            trackIndex.set(1.toString())
            artistName.set(it.artist)
            trackCover.set(it.coverURL)
        }
    }

    fun trackOnClick(view: View) {
        isplaying.set(!isplaying.get())
        (item as MusicRankEntity.Song).run {
            if (isplaying.get()) {
                lifecycleProvider.execute(addPlaylistItemCase,
                    AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlist_id = Constant.DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                        track_uri = url,
                        track_name = title,
                        artist_name = artist,
                        cover_url = coverURL,
                        lyric_url = lyricURL,
                        duration = length))) {
                    onNext { logw(it) }
                    onComplete {
                        MusicPlayer.instance.apply {
                            if (isPlaying()) stop()
                            play(url)
                        }
                    }
                }
            }
            else {
                MusicPlayer.instance.pause()
            }

        }
    }
}