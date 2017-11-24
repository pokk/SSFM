package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.chart.ChartRankChartDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.FetchMusicRankCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   10/26/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartRankChartDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(getMusicRankUsecase: FetchMusicRankCase) =
        ChartRankChartDetailFragmentViewModel(getMusicRankUsecase)
}