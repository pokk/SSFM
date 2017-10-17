package taiwan.no1.app.ssfm.mvvm.viewmodels

import de.umass.lastfm.Album
import de.umass.lastfm.Image
import de.umass.lastfm.PaginatedResult
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistImagesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase


/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchIndexViewModel(private val topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>,
                                   private val topTracksUsecase: BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue>,
                                   private val topAlbumsUsecase: BaseUsecase<Collection<Album>, GetTopAlbumsCase.RequestValue>,
                                   private val artistImagesUsecase: BaseUsecase<PaginatedResult<Image>, GetArtistImagesCase.RequestValue>):
    BaseViewModel() {
    fun fetchArtistList(callback: (List<ArtistEntity.Artist>) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase) { onNext { callback(it.artists.artists ?: emptyList()) } }

    fun fetchTrackList(callback: (List<TrackEntity.Track>) -> Unit) =
        lifecycleProvider.execute(topTracksUsecase) { onNext { callback(it.track.tracks ?: emptyList()) } }
}