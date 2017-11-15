package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.playlist.PlaylistDetailFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   11/14/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class PlaylistDetailFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(getPlaylistItemsUsecase: BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>):
        PlaylistDetailFragmentViewModel = PlaylistDetailFragmentViewModel(getPlaylistItemsUsecase)
}