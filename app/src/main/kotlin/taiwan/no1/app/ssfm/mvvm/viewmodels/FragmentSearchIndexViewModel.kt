package taiwan.no1.app.ssfm.mvvm.viewmodels

import com.devrapid.kotlinknifer.logd
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
    fun test() {
//        thread {
//            lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsCase.RequestValue(1)) {
//                onNext {
//                    it.toList()[0].mbid
//                }
//                onComplete {
//
//                }
//            }
//            lifecycleProvider.execute(topTracksUsecase, GetTopTracksCase.RequestValue(1)) {
//                onNext { logw(it) }
//            }
//        }

        thread {
            lifecycleProvider.execute(artistImagesUsecase,
                GetArtistImagesCase.RequestValue("faf4cefb-036c-4c88-b93a-5b03dd0a0e6b")) {
                onNext { logd(it.pageResults.toList()) }
            }
        }
    }
}