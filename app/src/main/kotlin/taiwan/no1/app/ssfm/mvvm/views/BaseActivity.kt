package taiwan.no1.app.ssfm.mvvm.views

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
    /** For providing to support fragments. */
    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    /** For providing to fragments. */
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

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

    /**
     * Providing the fragment injector([Fragment]) for the fragments.
     *
     * @return a [supportFragmentInjector] for children of this fragment.
     */
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = this.supportFragmentInjector

    /**
     * Providing the fragment injector([android.app.Fragment]) for the fragments.
     *
     * @return a [fragmentInjector] for children of this fragment.
     */
    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = this.fragmentInjector
}