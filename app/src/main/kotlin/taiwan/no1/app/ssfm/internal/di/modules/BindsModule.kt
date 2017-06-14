package taiwan.no1.app.ssfm.internal.di.modules

import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import taiwan.no1.app.ssfm.internal.di.annotations.PerActivity
import taiwan.no1.app.ssfm.internal.di.components.NetComponent
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import taiwan.no1.app.ssfm.mvvm.ui.activities.MainActivity
import taiwan.no1.app.ssfm.mvvm.ui.activities.TestActivity
import javax.inject.Singleton

/**
 *
 * @author  jieyi
 * @since   6/13/17
 */
@Module(subcomponents = arrayOf(NetComponent::class))
abstract class BindsModule {
    @Singleton
    @Binds
    @IntoMap
    @ClassKey
    abstract fun myActivityInjectorFactory(builder: NetComponent.Builder): AndroidInjector.Factory<out RemoteDataStore>
//    abstract fun contributeNetInjector(): RemoteDataStore

    @PerActivity
    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

    @PerActivity
    @ContributesAndroidInjector(modules = arrayOf(ActivityModule::class))
    abstract fun contributeTestActivityInjector(): TestActivity
}