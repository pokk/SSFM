package taiwan.no1.app.ssfm

import android.content.Context
import android.support.multidex.MultiDex
import com.devrapid.kotlinknifer.SharedPrefs
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import org.jetbrains.anko.defaultSharedPreferences
import taiwan.no1.app.ssfm.controllers.services.InitializeService
import taiwan.no1.app.ssfm.internal.di.components.AppComponent
import taiwan.no1.app.ssfm.internal.di.components.DaggerAppComponent

/**
 * Android Main Application.
 *
 * @author  jieyi
 * @since   5/9/17
 */
class App : DaggerApplication() {
    companion object {
        lateinit var injector: AndroidInjector<App>
        val appComponent by lazy { injector as AppComponent }
        val compactContext by lazy { appComponent.context() }
    }

    init {
        // Create an application component injector.
        injector = DaggerAppComponent.builder().create(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this)  // To fix the dex files over the top.
    }

    override fun onCreate() {
        super.onCreate()

        // Initial the shared preferences.
        SharedPrefs.setPrefSettings(defaultSharedPreferences)
        // Initial necessary lib by intent service.
        InitializeService.start(this)
    }

    override fun onTerminate() {
        super.onTerminate()
        FlowManager.destroy()
    }

    override fun applicationInjector(): AndroidInjector<App> = injector
}