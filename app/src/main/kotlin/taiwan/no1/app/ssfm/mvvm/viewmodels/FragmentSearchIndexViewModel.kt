package taiwan.no1.app.ssfm.mvvm.viewmodels

import com.devrapid.kotlinknifer.logw
import de.umass.lastfm.Album
import de.umass.lastfm.Image
import de.umass.lastfm.PaginatedResult
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.ExtArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.ExtTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
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
    fun fetchArtistList(callback: (List<ExtArtistEntity>) -> Unit) {
        lifecycleProvider.execute(topArtistsUsecase) {
            onNext { logw(it) }
//            onNext { callback.invoke(it.map { ExtArtistEntity(it) }) }
        }
    }

    fun fetchTrackList(callback: (List<ExtTrackEntity>) -> Unit) {
        lifecycleProvider.execute(topTracksUsecase) {
            //            onNext { callback.invoke(it.map { ExtTrackEntity(it) }) }
            onNext { logw(it) }
        }
    }
}