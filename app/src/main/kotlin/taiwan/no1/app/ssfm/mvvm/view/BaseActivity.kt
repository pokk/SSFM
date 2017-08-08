package taiwan.no1.app.ssfm.mvvm.view

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import com.trello.rxlifecycle2.components.RxActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Base activity for collecting all common methods here.
 *
 * @author  jieyi
 * @since   5/9/17
 */
abstract class BaseActivity: RxActivity(), HasFragmentInjector, HasSupportFragmentInjector {
    // Copy from [DaggerAppCompatActivity], becz this cant inherit two classes.
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    //region Activity lifecycle
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }
    //endregion

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = this.supportFragmentInjector

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = this.frameworkFragmentInjector
}