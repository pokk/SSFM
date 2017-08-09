package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.modules.NetModule
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import javax.inject.Singleton

/**
 * A component whose lifetime is the same as application for injecting to [RemoteDataStore].
 *
 * @author  Jieyi
 * @since   12/8/16
 */

@Singleton
@Component(modules = arrayOf(NetModule::class))
interface NetComponent {
    object Initializer {
        fun init(): NetComponent = DaggerNetComponent.builder()
            // XXX(jieyi): 8/9/17 This component should be depended AppComponent & using App() is dangerous.
            .netModule(NetModule(App()))
            .build()
    }

    fun inject(remoteDataStore: RemoteDataStore)
}