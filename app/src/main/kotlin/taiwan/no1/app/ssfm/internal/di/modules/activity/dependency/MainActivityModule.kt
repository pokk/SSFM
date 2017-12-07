package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.main.MainViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.models.data.repositories.DataRepository
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.FetchMusicDetailCase
import taiwan.no1.app.ssfm.models.usecases.GetDetailMusicUsecase

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class MainActivityModule {
    /**
     * Providing a [BaseUsecase] to the [MainViewModel].
     *
     * @param dataRepository get a repository object by dagger 2.
     * @return a [GetDetailMusicUsecase] but the data type is abstract class, we'd like to developer
     *         to use the abstract method directly.
     */
    @Provides
    @PerActivity
    fun provideUsecase(dataRepository: DataRepository): FetchMusicDetailCase = GetDetailMusicUsecase(dataRepository)

    /**
     * Providing a [MainViewModel] to the [MainActivity].
     *
     * @param context originally from activity module.
     * @param usecase receive from [provideUsecase] method.
     * @return a important [MainViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context, usecase: FetchMusicDetailCase) =
        MainViewModel(context, usecase)
}