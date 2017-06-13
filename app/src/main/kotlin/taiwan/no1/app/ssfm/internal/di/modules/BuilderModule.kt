package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import taiwan.no1.app.ssfm.internal.di.annotations.PerActivity
import taiwan.no1.app.ssfm.mvvm.ui.activities.TestActivity

/**
 *
 * @author  jieyi
 * @since   6/13/17
 */
@Module
//@Module(subcomponents = arrayOf(ActivityComponent::class))
abstract class BuilderModule {
//    @Binds
//    @IntoMap
//    @ActivityKey(BaseActivity::class)
//    abstract fun bindBaseActivityInjectorFactory(builder: ActivityComponent.Builder): AndroidInjector.Factory<out Activity>

//    @Binds
//    @IntoMap
//    @ActivityKey(TestActivity::class)
//    abstract fun bindTestActivityInjectorFactory(builder: ActivityComponent.Builder): AndroidInjector.Factory<out Activity>

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeTestActivityInjector(): TestActivity
}