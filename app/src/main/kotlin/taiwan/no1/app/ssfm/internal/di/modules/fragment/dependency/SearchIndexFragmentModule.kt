package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchIndexViewModel

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
    fun provideViewModel(topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>,
                         topTracksUsecase: BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue>):
        FragmentSearchIndexViewModel = FragmentSearchIndexViewModel(topArtistsUsecase, topTracksUsecase)
}