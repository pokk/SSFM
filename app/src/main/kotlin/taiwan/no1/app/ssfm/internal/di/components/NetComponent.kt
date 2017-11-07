package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.Network
import taiwan.no1.app.ssfm.internal.di.modules.NetModule
import taiwan.no1.app.ssfm.models.data.remote.RemoteDataStore

/**
 * A component whose lifetime is the same as application for injecting to [RemoteDataStore].
 *
 * @author  Jieyi
 * @since   12/8/16
 */
@Network
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(NetModule::class))
interface NetComponent {
    object Initializer {
        fun init(): NetComponent = DaggerNetComponent.builder()
            .appComponent(App.appComponent)
            .netModule(NetModule())
            .build()
    }

    fun inject(remoteDataStore: RemoteDataStore)
}