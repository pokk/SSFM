package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.LocalData
import taiwan.no1.app.ssfm.internal.di.modules.RoomModule
import taiwan.no1.app.ssfm.mvvm.models.data.local.LocalDataStore

/**
 *
 * @author  jieyi
 * @since   8/16/17
 */
@LocalData
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(RoomModule::class))
interface DatabaseComponent {
    object Initializer {
        fun init(): DatabaseComponent = DaggerDatabaseComponent.builder()
            .appComponent(App.appComponent)
            .roomModule(RoomModule())
            .build()
    }

    fun inject(localDataStore: LocalDataStore)
}