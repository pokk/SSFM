package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentChartTagDetailViewModel

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   10/26/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartTagDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(tagInfoUsecase: BaseUsecase<TagEntity, GetTagInfoCase.RequestValue>,
                         topAlbumsUsecase: BaseUsecase<TopAlbumEntity, GetTagTopAlbumsCase.RequestValue>,
                         topArtistsUsecase: BaseUsecase<TagTopArtistEntity, GetTagTopArtistsCase.RequestValue>,
                         topTracksUsecase: BaseUsecase<TopTrackEntity, GetTagTopTracksCase.RequestValue>):
        FragmentChartTagDetailViewModel =
        FragmentChartTagDetailViewModel(tagInfoUsecase, topAlbumsUsecase, topArtistsUsecase, topTracksUsecase)
}