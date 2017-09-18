package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.ChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.ChartActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class ChartActivityModule {
    /**
     * Providing a [ChartViewModel] to the [ChartActivity].
     *
     * @param activity  originally from activity module.
     * @return a important [ChartViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: ChartActivity): ChartViewModel = ChartViewModel(activity)
}