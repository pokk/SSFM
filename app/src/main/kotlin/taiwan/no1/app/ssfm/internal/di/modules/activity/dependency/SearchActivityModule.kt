package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SaveKeywordHistoryCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase
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
    fun provideSearchUsecase(dataRepository: DataRepository): BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue> =
        SearchMusicCase(dataRepository)

    /**
     * Providing a [BaseUsecase] to the [SearchViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideSaveUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue> =
        SaveKeywordHistoryCase(dataRepository)

    /**
     * Providing a [BaseUsecase] to the [SearchViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideGetUsecase(dataRepository: DataRepository): BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue> =
        GetKeywordHistoriesCase(dataRepository)

    /**
     * Providing a [BaseUsecase] to the [SearchViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideDeleteUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue> =
        RemoveKeywordHistoriesCase(dataRepository)

    /**
     * Providing a [SearchViewModel] to the [SearchActivity].
     *
     * @param activity originally from activity module.
     * @return a important [SearchViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(activity: SearchActivity,
                         searchUsecase: BaseUsecase<SearchMusicEntity, SearchMusicCase.RequestValue>,
                         addHistoryUsecase: BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue>,
                         getHistoriesUsecase: BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>,
                         deleteHistoriesUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>): SearchViewModel =
        SearchViewModel(activity, searchUsecase, addHistoryUsecase, getHistoriesUsecase, deleteHistoriesUsecase)
}