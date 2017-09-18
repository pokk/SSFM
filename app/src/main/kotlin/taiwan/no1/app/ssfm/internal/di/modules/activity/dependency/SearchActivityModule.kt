package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.SearchActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class SearchActivityModule {
    /**
     * Providing a [SearchViewModel] to the [SearchActivity].
     *
     * @param activity  originally from activity module.
     * @return a important [SearchViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: SearchActivity): SearchViewModel = SearchViewModel(activity)
}