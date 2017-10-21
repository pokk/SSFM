package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTagEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTagsCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartIndexViewModel

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartIndexFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>,
                         topTagsUsecase: BaseUsecase<ChartTopTagEntity, GetTopTagsCase.RequestValue>):
        FragmentChartIndexViewModel = FragmentChartIndexViewModel(topArtistsUsecase, topTagsUsecase)
}