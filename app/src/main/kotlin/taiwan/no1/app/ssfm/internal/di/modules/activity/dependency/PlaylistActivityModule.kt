package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.playlist.PlaylistViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistsUsecase
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class PlaylistActivityModule {
    /**
     * Providing a [BaseUsecase] to the [PlaylistViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideAddPlaylistUsecase(dataRepository: DataRepository): BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue> =
        AddPlaylistUsecase(dataRepository)

    /**
     * Providing a [BaseUsecase] to the [PlaylistViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    @Named("activity_get_playlist")
    fun provideGetPlaylistsUsecase(dataRepository: DataRepository): BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue> =
        GetPlaylistsUsecase(dataRepository)

    /**
     * Providing a [PlaylistViewModel] to the [PlaylistActivity].
     *
     * @param context originally from activity module.
     * @return a important [PlaylistViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context,
                         @Named("activity_get_playlist")
                         getPlaylistsUsecase: BaseUsecase<List<PlaylistEntity>, AddPlaylistUsecase.RequestValue>,
                         addPlaylistUsecase: BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue>): PlaylistViewModel =
        PlaylistViewModel(context, getPlaylistsUsecase, addPlaylistUsecase)
}