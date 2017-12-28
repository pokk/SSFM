package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import com.devrapid.kotlinknifer.loge
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.FetchArtistInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopAlbumOfArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackOfArtistCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopAlbumsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopTracksUsecase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartArtistDetailFragmentViewModel(private val artistsInfoUsecase: FetchArtistInfoCase,
                                         private val artistTopTracksUsecase: FetchTopTrackOfArtistCase,
                                         private val artistTopAlbumsUsecase: FetchTopAlbumOfArtistCase) : BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val artistImage by lazy { ObservableField<String>() }
    val artistSummary by lazy { ObservableField<String>() }

    fun fetchDetailInfo(mbid: String, name: String, callback: (artistDetailInfo: ArtistEntity) -> Unit) {
        lifecycleProvider.execute(artistsInfoUsecase, GetArtistInfoUsecase.RequestValue(name, mbid)) {
            onNext {
                it.artist.let {
                    artistImage.set(it?.images?.get(EXTRA_LARGE)?.text.orEmpty())
                    artistName.set(it?.name.orEmpty())
                    artistSummary.set(it?.bio?.content.orEmpty())
                }
                callback(it)
            }
        }
    }

    fun fetchHotTracks(name: String, callback: (entity: List<TrackEntity.TrackWithStreamableString>) -> Unit) {
        lifecycleProvider.execute(artistTopTracksUsecase, GetArtistTopTracksUsecase.RequestValue(name)) {
            onNext { callback(it.toptracks.tracks) }
        }
    }

    fun fetchHotAlbum(name: String, callback: (entity: List<AlbumEntity.AlbumWithPlaycount>) -> Unit) {
        lifecycleProvider.execute(artistTopAlbumsUsecase, GetArtistTopAlbumsUsecase.RequestValue(name)) {
            onNext {
                it.topalbums.albums.apply {
                    forEachIndexed { index, albumWithPlaycount ->
                        albumWithPlaycount.index = index
                    }
                }.let(callback)
            }
            onError { loge(it.message) }
        }
    }
}