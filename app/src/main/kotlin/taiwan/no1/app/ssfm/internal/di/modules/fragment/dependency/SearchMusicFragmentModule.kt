package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.search.SearchResultFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.SearchMusicV1Case

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class SearchMusicFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(searchUsecase: SearchMusicV1Case) = SearchResultFragmentViewModel(searchUsecase)
}