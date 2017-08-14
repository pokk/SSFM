package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.DetailMusicCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.DetailMusicCase.RequestValue
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
    fun usecase(dataRepository: DataRepository): BaseUsecase<DetailMusicEntity, RequestValue> =
        DetailMusicCase(dataRepository)

    @Provides
    @PerActivity
    fun vm(activity: MainActivity, usecase: BaseUsecase<DetailMusicEntity, RequestValue>): MainViewModel =
        MainViewModel(activity, usecase)
}