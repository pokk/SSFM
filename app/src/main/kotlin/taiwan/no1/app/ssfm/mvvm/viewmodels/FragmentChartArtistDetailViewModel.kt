package taiwan.no1.app.ssfm.mvvm.viewmodels

import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartArtistDetailViewModel(private val artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                                         private val topTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetTopAlbumsCase.RequestValue>):
    BaseViewModel()