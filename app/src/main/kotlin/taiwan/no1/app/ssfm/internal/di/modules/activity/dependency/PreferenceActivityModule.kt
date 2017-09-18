package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.PreferenceViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.PreferenceActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class PreferenceActivityModule {
    /**
     * Providing a [PreferenceViewModel] to the [PreferenceActivity].
     *
     * @param activity  originally from activity module.
     * @return a important [PreferenceViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: PreferenceActivity): PreferenceViewModel = PreferenceViewModel(activity)
}