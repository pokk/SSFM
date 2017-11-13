package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.search.SearchHistoryFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetKeywordHistoriesCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class SearchHistoriesFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(context: Context,
                         getHistoriesUsecase: BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>):
        SearchHistoryFragmentViewModel = SearchHistoryFragmentViewModel(context,
        getHistoriesUsecase)
}