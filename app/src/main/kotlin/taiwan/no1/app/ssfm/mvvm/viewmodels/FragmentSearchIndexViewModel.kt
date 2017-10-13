package taiwan.no1.app.ssfm.mvvm.viewmodels

import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Image
import de.umass.lastfm.PaginatedResult
import de.umass.lastfm.Track
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistImagesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase
import kotlin.concurrent.thread


/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchIndexViewModel(private val topArtistsUsecase: BaseUsecase<Collection<Artist>, GetTopArtistsCase.RequestValue>,
                                   private val topTracksUsecase: BaseUsecase<Collection<Track>, GetTopTracksCase.RequestValue>,
                                   private val topAlbumsUsecase: BaseUsecase<Collection<Album>, GetTopAlbumsCase.RequestValue>,
                                   private val artistImagesUsecase: BaseUsecase<PaginatedResult<Image>, GetArtistImagesCase.RequestValue>):
    BaseViewModel() {
    fun fetchArtistList(callback: (List<Artist>) -> Unit) {
        thread {
            lifecycleProvider.execute(topArtistsUsecase) { onNext { callback.invoke(it.toList()) } }
        }
    }

    fun fetchTrackList(callback: (List<Track>) -> Unit) {
        thread {
            lifecycleProvider.execute(topTracksUsecase) { onNext { callback.invoke(it.toList()) } }
        }
    }
}