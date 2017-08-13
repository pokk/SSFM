package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.viewmodels.MainViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.MainActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class MainActivityModule {
    @Provides
    @PerActivity
    fun vm(activity: MainActivity, dataRepository: DataRepository): MainViewModel =
        MainViewModel(activity, dataRepository)
}