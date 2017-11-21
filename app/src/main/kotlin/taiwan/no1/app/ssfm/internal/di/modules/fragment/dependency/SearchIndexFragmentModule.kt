package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.search.SearchIndexFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackCase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class SearchIndexFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(topArtistsUsecase: FetchTopArtistCase, topTracksUsecase: FetchTopTrackCase) =
        SearchIndexFragmentViewModel(topArtistsUsecase, topTracksUsecase)
}