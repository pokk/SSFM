package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV2Case
import taiwan.no1.app.ssfm.models.usecases.v2.SearchMusicUsecase

/**
 *
 * @author  jieyi
 * @since   10/29/17
 */
class RecyclerViewChartArtistHotTrackViewModel(private val searchMusicCase: SearchMusicV2Case,
                                               private val addPlaylistItemCase: AddPlaylistItemCase,
                                               private val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }

    init {
        (item as TrackEntity.TrackWithStreamableString).let {
            trackName.set(it.name)
            trackNumber.set(it.attr?.rank ?: 0.toString())
        }
    }

    fun trackOnClick(view: View) {
        (item as TrackEntity.TrackWithStreamableString).let {
            val trackName = it.name
            val artistName = it.artist?.name

            (trackName to artistName).takeUnless { it.first.isNullOrBlank() || it.second.isNullOrBlank() }.let {
                lifecycleProvider.execute(searchMusicCase, SearchMusicUsecase.RequestValue("$artistName $trackName")) {
                    onNext {
                        it.data.items.first().run {
                            MusicPlayerHelper.instance.play(url) {
                                lifecycleProvider.execute(addPlaylistItemCase,
                                    AddPlaylistItemUsecase.RequestValue(PlaylistItemEntity(playlistId = DATABASE_PLAYLIST_HISTORY_ID.toLong(),
                                        trackUri = url,
                                        trackName = title,
                                        artistName = artist,
                                        coverUrl = coverURL,
                                        lyricUrl = lyricURL,
                                        duration = length))) { onNext { logw(it) } }
                            }
                        }
                    }
                }
            }
        }
    }
}