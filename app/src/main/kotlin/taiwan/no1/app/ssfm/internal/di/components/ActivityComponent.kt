package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.internal.di.annotations.PerActivity
import taiwan.no1.app.ssfm.internal.di.modules.ActivityModule

/**
 * A component whose lifetime is the life of the Activity.
 *
 * @author  jieyi
 * @since   5/11/17
 */
@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    object Initializer {
        fun init(appComponent: AppComponent): ActivityComponent = DaggerActivityComponent.builder()
                .appComponent(appComponent)
                .activityModule(ActivityModule())
                .build()
    }

    /**
     * After injected an activity, the presenter of the activity should be provided in [ActivityModule].
     */
}