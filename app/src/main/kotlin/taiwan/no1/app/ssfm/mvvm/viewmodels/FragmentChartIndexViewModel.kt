package taiwan.no1.app.ssfm.mvvm.viewmodels

import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTagEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTagsCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartIndexViewModel(private val topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>,
                                  private val topTagsUsecase: BaseUsecase<ChartTopTagEntity, GetTopTagsCase.RequestValue>):
    BaseViewModel()