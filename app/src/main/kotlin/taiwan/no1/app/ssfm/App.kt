package taiwan.no1.app.ssfm

import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import taiwan.no1.app.ssfm.internal.di.components.AppComponent

/**
 * Android Main Application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
class App: DaggerApplication() {
    companion object {
        lateinit var injector: AndroidInjector<App>
        val appComponent by lazy { injector as AppComponent }
    }

    init {
        injector = DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()

//        val db = Room.databaseBuilder(this.applicationContext, TestDatabase::class.java, "TestDatabase").
//            allowMainThreadQueries().
//            build()
//
//        logw(db.getTestDao().findAll())
    }

    override fun applicationInjector(): AndroidInjector<App> = injector
}