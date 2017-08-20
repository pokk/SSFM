package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

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
    /**
     * Providing a [BaseUsecase] to the [MainViewModel].
     *
     * @param dataRepository    get a repository object by dagger 2.
     * @return  a [DetailMusicCase] but the data type is abstract class, we'd like to developer
     *          to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideUsecase(dataRepository: DataRepository): BaseUsecase<DetailMusicEntity, RequestValue> =
        DetailMusicCase(dataRepository)

    /**
     * Providing a [MainViewModel] to the [MainActivity].
     *
     * @param activity  originally from activity module.
     * @param usecase   receive from [provideUsecase] method.
     * @return a important [MainViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: MainActivity, usecase: BaseUsecase<DetailMusicEntity, RequestValue>): MainViewModel =
        MainViewModel(activity, usecase)
}