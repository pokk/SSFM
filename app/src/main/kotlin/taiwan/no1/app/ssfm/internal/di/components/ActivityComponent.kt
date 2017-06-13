package taiwan.no1.app.ssfm.internal.di.components

import dagger.Subcomponent
import dagger.android.AndroidInjector
import taiwan.no1.app.ssfm.internal.di.annotations.PerActivity
import taiwan.no1.app.ssfm.internal.di.modules.ActivityModule
import taiwan.no1.app.ssfm.mvvm.ui.activities.TestActivity


/**
 * A component whose lifetime is the life of the Activity.
 *
 * @author  jieyi
 * @since   5/11/17
 */
@PerActivity
@Subcomponent(modules = arrayOf(ActivityModule::class))
interface ActivityComponent: AndroidInjector<TestActivity> {
    /**
     * After injected an activity, the presenter of the activity should be provided in [ActivityModule].
     */
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<TestActivity>()
}