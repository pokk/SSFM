package taiwan.no1.app.ssfm.mvvm.viewmodels

import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartIndexViewModel(private val topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>
//                                  private val topTracksUsecase: BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue>
):
    BaseViewModel()