package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.features.chart.ChartRankChartDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.EditRankChartCase
import taiwan.no1.app.ssfm.models.usecases.FetchMusicRankCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   10/26/17
 */
@Module(includes = [UseCaseModule::class])
class ChartRankChartDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(getMusicRankUsecase: FetchMusicRankCase, editRankChartUsecase: EditRankChartCase) =
        ChartRankChartDetailFragmentViewModel(getMusicRankUsecase, editRankChartUsecase)
}