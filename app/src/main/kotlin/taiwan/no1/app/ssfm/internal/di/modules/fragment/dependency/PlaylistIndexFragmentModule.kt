package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.playlist.PlaylistIndexFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class PlaylistIndexFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(getPlaylistsUsecase: BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue>,
                         getPlaylistItemsUsecase: BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>):
        PlaylistIndexFragmentViewModel = PlaylistIndexFragmentViewModel(getPlaylistsUsecase, getPlaylistItemsUsecase)
}