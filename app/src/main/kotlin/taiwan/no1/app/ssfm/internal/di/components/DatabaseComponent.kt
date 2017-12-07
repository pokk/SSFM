package taiwan.no1.app.ssfm.internal.di.components

import dagger.Component
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.LocalData
import taiwan.no1.app.ssfm.internal.di.modules.DatabaseModule
import taiwan.no1.app.ssfm.models.data.local.LocalDataStore

/**
 *
 * @author  jieyi
 * @since   8/16/17
 */
@LocalData
@Component(dependencies = [AppComponent::class], modules = [DatabaseModule::class])
interface DatabaseComponent {
    object Initializer {
//        fun rendered(): DatabaseComponent = DaggerDatabaseComponent.builder()
//            .appComponent(App.appComponent)
//            .roomModule(DatabaseModule())
//            .build()
    }

    fun inject(localDataStore: LocalDataStore)
}