package taiwan.no1.app.ssfm.mvvm.views

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.RxActivity
import com.yalantis.guillotine.animation.GuillotineAnimation
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.part_toolbar_menu.iv_content_hamburger
import kotlinx.android.synthetic.main.part_toolbar_menu.tb_toolbar
import kotlinx.android.synthetic.main.part_toolbar_menu.view.iv_content_hamburger
import taiwan.no1.app.ssfm.R
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
    val menu by lazy { layoutInflater.inflate(R.layout.page_menu, null) }
    protected val navigator by lazy { Navigator(this) }
    private val rootView by lazy { findViewById(R.id.root) as? ViewGroup }

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

    protected fun attachMenuView() {
        this.rootView?.also {
            it.addView(this.menu)
            GuillotineAnimation.GuillotineBuilder(this.menu, this.menu.iv_content_hamburger, iv_content_hamburger)
                .setStartDelay(250)
                .setActionBarViewForAnimation(tb_toolbar)
                .setClosedOnStart(true)
                .build()
        }
    }
}