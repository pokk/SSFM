package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment

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
    fun provideViewModel(topArtistsUsecase: BaseUsecase<TopArtistEntity, GetTopArtistsCase.RequestValue>,
                         topTracksUsecase: BaseUsecase<TopTrackEntity, GetTopTracksCase.RequestValue>):
        FragmentSearchIndexViewModel = FragmentSearchIndexViewModel(topArtistsUsecase, topTracksUsecase)
}