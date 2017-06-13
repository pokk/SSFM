package taiwan.no1.app.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.internal.di.modules.NetModule
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import javax.inject.Singleton

/**
 *
 * @author  Jieyi
 * @since   12/8/16
 */

@Singleton
@Component(modules = arrayOf(NetModule::class))
interface NetComponent {
//    object Initializer {
//        @JvmStatic fun init(): NetComponent = DaggerNetComponent.builder()
//            .netModule(NetModule(App.getAppContext()))
//            .build()
//    }

    fun inject(remoteDataStore: RemoteDataStore)
}
