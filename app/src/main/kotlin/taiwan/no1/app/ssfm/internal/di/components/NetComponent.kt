package taiwan.no1.app.ssfm.internal.di.components

import dagger.Subcomponent
import dagger.android.AndroidInjector
import taiwan.no1.app.ssfm.internal.di.modules.NetModule
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import javax.inject.Singleton

/**
 *
 * @author  Jieyi
 * @since   12/8/16
 */

//@Singleton
//@Component(modules = arrayOf(NetModule::class))
//interface NetComponent {
//    object Initializer {
//        @JvmStatic fun init(): NetComponent = DaggerNetComponent.builder()
//            .netModule(NetModule(App.getAppContext()))
//            .build()
//    }
//
//    fun inject(remoteDataStore: RemoteDataStore)
//}

@Singleton
@Subcomponent(modules = arrayOf(NetModule::class))
interface NetComponent: AndroidInjector<RemoteDataStore> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<RemoteDataStore>()
}