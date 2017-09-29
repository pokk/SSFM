package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity
import taiwan.no1.app.ssfm.mvvm.viewmodels.PlaylistViewModel
import taiwan.no1.app.ssfm.mvvm.views.activities.PlaylistActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class PlaylistActivityModule {
    /**
     * Providing a [PlaylistViewModel] to the [PlaylistActivity].
     *
     * @param context originally from activity module.
     * @return a important [PlaylistViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context): PlaylistViewModel = PlaylistViewModel(context)
}