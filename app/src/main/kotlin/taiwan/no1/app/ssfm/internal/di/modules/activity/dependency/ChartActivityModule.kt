package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.features.chart.ChartViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistsUsecase
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class ChartActivityModule {
    /**
     * Providing a [BaseUsecase] to the [ChartViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [AddPlaylistItemCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideAddPlaylistItemUsecase(dataRepository: DataRepository): AddPlaylistItemCase =
        AddPlaylistItemUsecase(dataRepository)

    /**
     * Providing a [BaseUsecase] to the [ChartViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [GetPlaylistsUsecase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    // NOTE(jieyi): 12/30/17 Becz fragment's children scope have the same data-type was provided.
    @Named("activity_playlist_usecase")
    fun provideGetPlaylistsUsecase(dataRepository: DataRepository): FetchPlaylistCase =
        GetPlaylistsUsecase(dataRepository)

    /**
     * Providing a [ChartViewModel] to the [ChartActivity].
     *
     * @param context originally from activity module.
     * @return a important [ChartViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context) = ChartViewModel(context)
}