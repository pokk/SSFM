package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.internal.di.annotations.PerFragment
import taiwan.no1.app.ssfm.internal.di.modules.FragmentModule

/**
 * A component whose lifetime is the life of the Fragment.
 *
 * @author  jieyi
 * @since   5/11/17
 */
@PerFragment
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(FragmentModule::class))
interface FragmentComponent {
    object Initializer {
        fun init(appComponent: AppComponent): FragmentComponent = DaggerFragmentComponent.builder()
            .appComponent(appComponent)
            .fragmentModule(FragmentModule())
            .build()
    }

    /**
     * After injected an activity, the presenter of the activity should be provided in [FragmentModule].
     */
}