package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase.RequestValue
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
     * Providing a [BaseUsecase] to the [SearchViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideUsecase(dataRepository: DataRepository): BaseUsecase<SearchMusicEntity, RequestValue> =
        SearchMusicCase(dataRepository)

    /**
     * Providing a [SearchViewModel] to the [SearchActivity].
     *
     * @param activity originally from activity module.
     * @return a important [SearchViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: SearchActivity,
                         usecase: BaseUsecase<SearchMusicEntity, RequestValue>): SearchViewModel =
        SearchViewModel(activity, usecase)
}