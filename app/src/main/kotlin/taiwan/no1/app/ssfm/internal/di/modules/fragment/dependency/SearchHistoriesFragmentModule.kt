package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.search.SearchHistoryFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.FetchSearchHistoryCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = [UseCaseModule::class])
class SearchHistoriesFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(context: Context, getHistoriesUsecase: FetchSearchHistoryCase) =
        SearchHistoryFragmentViewModel(context, getHistoriesUsecase)
}