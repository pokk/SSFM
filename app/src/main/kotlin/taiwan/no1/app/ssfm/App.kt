package taiwan.no1.app.ssfm

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.support.multidex.MultiDex
import com.raizlabs.android.dbflow.config.FlowManager
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import taiwan.no1.app.ssfm.controllers.services.InitializeService
import taiwan.no1.app.ssfm.internal.di.components.AppComponent
import taiwan.no1.app.ssfm.internal.di.components.DaggerAppComponent
import taiwan.no1.app.ssfm.misc.notification.NotificationService

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

        // Initial necessary lib by intent service.
        InitializeService.start(this)

        // Initial notification service
        val intent = Intent(this, NotificationService::class.java)
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private var notificationService: NotificationService? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            notificationService = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            notificationService = (service as NotificationService.NotificationBinder).getService()
            notificationService?.initNotification()
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        FlowManager.destroy()
    }

    override fun applicationInjector(): AndroidInjector<App> = injector
}