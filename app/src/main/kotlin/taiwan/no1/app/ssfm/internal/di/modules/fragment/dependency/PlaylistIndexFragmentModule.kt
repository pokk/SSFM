package taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency

import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.features.playlist.PlaylistIndexFragmentViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerFragment
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.RemovePlaylistCase
import javax.inject.Named

/**
 * A base component upon which fragment's components may depend. Activity-level components should extend this component.
 *
 * @author  jieyi
 * @since   8/11/17
 */
@Module(includes = [UseCaseModule::class])
class PlaylistIndexFragmentModule {
    @Provides
    @PerFragment
    fun provideViewModel(getPlaylistsUsecase: FetchPlaylistCase,
                         getPlaylistItemsUsecase: FetchPlaylistItemCase,
                         @Named("remove_playlist")
                         removePlaylistUsecase: RemovePlaylistCase) =
        PlaylistIndexFragmentViewModel(getPlaylistsUsecase, getPlaylistItemsUsecase, removePlaylistUsecase)
}