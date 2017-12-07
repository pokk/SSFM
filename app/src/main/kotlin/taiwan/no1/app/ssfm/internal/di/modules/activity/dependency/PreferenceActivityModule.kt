package taiwan.no1.app.ssfm.internal.di.modules.activity.dependency

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.functions.preference.PreferenceViewModel
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.PerActivity

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class PreferenceActivityModule {
    /**
     * Providing a [PreferenceViewModel] to the [PreferenceActivity].
     *
     * @param context originally from activity module.
     * @return a important [PreferenceViewModel] for binding view and viewmodel by activity.
     */
    @Provides
    @PerActivity
    fun provideViewModel(context: Context) = PreferenceViewModel(context)
}