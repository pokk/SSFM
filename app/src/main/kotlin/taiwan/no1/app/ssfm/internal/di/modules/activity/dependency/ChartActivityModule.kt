package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.chart.ChartViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.UseCaseModule

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartActivityModule {
    /**
     * Providing a [ChartViewModel] to the [ChartActivity].
     *
     * @param context originally from activity module.
     * @return a important [ChartViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context) = ChartViewModel(context)
}