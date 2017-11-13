package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import com.devrapid.kotlinknifer.loge
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopAlbumsCase
import taiwan.no1.app.ssfm.models.usecases.GetArtistTopTracksCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartArtistDetailFragmentViewModel(private val artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                                         private val artistTopTracksUsecase: BaseUsecase<ArtistTopTrackEntity, GetArtistTopTracksCase.RequestValue>,
                                         private val artistTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetArtistTopAlbumsCase.RequestValue>) :
    BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val artistImage by lazy { ObservableField<String>() }
    val artistSummary by lazy { ObservableField<String>() }

    fun fetchDetailInfo(mbid: String,
                        name: String,
                        callback: (artistDetailInfo: ArtistEntity) -> Unit) {
        lifecycleProvider.execute(artistsInfoUsecase, GetArtistInfoCase.RequestValue(name, mbid)) {
            onNext {
                it.artist.let {
                    artistImage.set(it?.images?.get(EXTRA_LARGE)?.text ?: "")
                    artistName.set(it?.name ?: "")
                    artistSummary.set(it?.bio?.content ?: "")
                }
                callback(it)
            }
        }
    }

    fun fetchHotTracks(name: String,
                       callback: (entity: List<TrackEntity.TrackWithStreamableString>) -> Unit) {
        lifecycleProvider.execute(artistTopTracksUsecase,
            GetArtistTopTracksCase.RequestValue(name)) {
            onNext { callback(it.toptracks.tracks) }
        }
    }

    fun fetchHotAlbum(name: String) {
        lifecycleProvider.execute(artistTopAlbumsUsecase,
            GetArtistTopAlbumsCase.RequestValue(name)) {
            onNext {
                it.topalbums.albums.forEach { }
            }
            onError { loge(it.message) }
        }
    }
}