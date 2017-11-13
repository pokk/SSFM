package taiwan.no1.app.ssfm.controllers.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasServiceInjector
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/24/17
 */
class BaseService : Service(), HasServiceInjector {
    @Inject lateinit var serviceInjector: DispatchingAndroidInjector<Service>

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun serviceInjector(): AndroidInjector<Service> = serviceInjector
}