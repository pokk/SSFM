package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.search.SearchViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.SaveKeywordHistoryCase

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
    fun provideSaveUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue> =
        SaveKeywordHistoryCase(dataRepository)

    /**
     * Providing a [SearchViewModel] to the [SearchActivity].
     *
     * @param context originally from activity module.
     * @return a important [SearchViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context,
                         addHistoryUsecase: BaseUsecase<Boolean, SaveKeywordHistoryCase.RequestValue>):
        SearchViewModel = SearchViewModel(context, addHistoryUsecase)
}