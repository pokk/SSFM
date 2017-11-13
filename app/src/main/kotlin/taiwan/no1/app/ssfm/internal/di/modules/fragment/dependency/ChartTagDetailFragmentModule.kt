package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.chart.ChartTagDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagTopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopAlbumsCase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopArtistsCase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopTracksCase

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
        ChartTagDetailFragmentViewModel =
        ChartTagDetailFragmentViewModel(tagInfoUsecase,
            topAlbumsUsecase,
            topArtistsUsecase,
            topTracksUsecase)
}