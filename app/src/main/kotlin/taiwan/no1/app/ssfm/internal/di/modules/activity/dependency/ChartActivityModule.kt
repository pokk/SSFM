package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.internal.di.modules.fragment.dependency.UseCaseModule
import taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistTopTracksCase
import taiwan.no1.app.ssfm.mvvm.viewmodels.ChartViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.ChartActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module(includes = arrayOf(UseCaseModule::class))
class ChartActivityModule {
    @Provides
    @PerActivity
    fun provideActivityArtistsInfoUsecase(dataRepository: DataRepository) = GetArtistInfoCase(dataRepository)

    @Provides
    @PerActivity
    fun provideActivityArtistTopTracksUsecase(dataRepository: DataRepository) = GetArtistTopTracksCase(dataRepository)

    @Provides
    @PerActivity
    fun provideActivityArtistTopAlbumsUsecase(dataRepository: DataRepository) = GetArtistTopAlbumsCase(dataRepository)

    /**
     * Providing a [ChartViewModel] to the [ChartActivity].
     *
     * @param context originally from activity module.
     * @param artistsInfoUsecase
     * @param artistTopTracksUsecase
     * @param artistTopAlbumsUsecase
     * @return a important [ChartViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context,
                         artistsInfoUsecase: GetArtistInfoCase,
                         artistTopTracksUsecase: GetArtistTopTracksCase,
                         artistTopAlbumsUsecase: GetArtistTopAlbumsCase): ChartViewModel
        = ChartViewModel(context, artistsInfoUsecase, artistTopTracksUsecase, artistTopAlbumsUsecase)
}