package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayer
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(private val searchMusicCase: SearchMusicV2Case,
                                           private val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }

    init {
        (item as TrackEntity.Track).let {
            trackName.set(it.name)
            trackNumber.set(it.attr?.rank ?: 0.toString())
            trackDuration.set(it.duration?.toInt()?.toTimeString())
        }
    }

    fun trackOnClick(view: View) {
        (item as TrackEntity.Track).let {
            val trackName = it.name
            val artistName = it.artist?.name

            (trackName to artistName).takeUnless { it.first.isNullOrBlank() || it.second.isNullOrBlank() }.let {
                lifecycleProvider.execute(searchMusicCase, SearchMusicUsecase.RequestValue("$artistName $trackName")) {
                    onNext {
                        logw(it.data.items.first().url)
                        MusicPlayer.instance.apply {
                            if (isPlaying()) stop()
                            play(it.data.items.first().url)
                        }
                    }
                }
            }
        }
    }
}