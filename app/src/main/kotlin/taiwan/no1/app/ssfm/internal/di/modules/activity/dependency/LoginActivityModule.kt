package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.LoginViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.MainViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.LoginActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.MainActivity

/**
 *
 * @author  jieyi
 * @since   13/9/17
 */
@Module
class LoginActivityModule {
    /**
     * Providing a [MainViewModel] to the [MainActivity].
     *
     * @param activity  originally from activity module.
     * @return a important [MainViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: LoginActivity): LoginViewModel = LoginViewModel(activity)
}