package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.main.MainActivity
import taiwan.no1.app.ssfm.functions.main.MainViewModel
import taiwan.no1.app.ssfm.functions.play.PlayMainViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class PlayMainActivityModule {
    /**
     * Providing a [MainViewModel] to the [MainActivity].
     *
     * @hashCode context originally from activity module.
     * @return a important [MainViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context) = PlayMainViewModel(context)
}