package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.features.chart.ChartViewModel
import taiwan.no1.app.ssfm.features.search.SearchViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistsUsecase
import taiwan.no1.app.ssfm.models.usecases.SaveKeywordHistoryUsecase
import taiwan.no1.app.ssfm.models.usecases.SaveSearchHistoryCase
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class SearchActivityModule {
    /**
     * Providing a [BaseUsecase] to the [SearchViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [SearchMusicCase] but the data type is abstract class, we'd like to developer
     * to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideSaveUsecase(dataRepository: DataRepository): SaveSearchHistoryCase =
        SaveKeywordHistoryUsecase(dataRepository)

    /**
     * Providing a [BaseUsecase] to the [SearchViewModel].
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
     * Providing a [SearchViewModel] to the [SearchActivity].
     *
     * @param context originally from activity module.
     * @return a important [SearchViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context, addHistoryUsecase: SaveSearchHistoryCase) =
        SearchViewModel(context, addHistoryUsecase)
}