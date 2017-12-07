package taiwan.no1.app.ssfm.functions.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v4.app.Fragment
import android.view.ViewGroup
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import com.trello.rxlifecycle2.components.RxActivity
import com.yalantis.guillotine.animation.GuillotineAnimation
import com.yalantis.guillotine.interfaces.GuillotineListener
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.part_toolbar_menu.tb_toolbar
import kotlinx.android.synthetic.main.part_toolbar_menu.view.iv_content_hamburger
import kotlinx.android.synthetic.main.part_toolbar_view.iv_content_hamburger
import taiwan.no1.app.ssfm.R
import javax.inject.Inject

/**
 * Base activity for collecting all common methods here.
 *
 * @author  jieyi
 * @since   5/9/17
 */
abstract class BaseActivity : RxActivity(), HasFragmentInjector, HasSupportFragmentInjector {
    // Copy from [DaggerAppCompatActivity], becz this cant inherit two classes.
    /** For providing to support searchFragments. */
    @Inject lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    /** For providing to searchFragments. */
    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<android.app.Fragment>
    val menu by lazy { layoutInflater.inflate(R.layout.page_menu_preference, null) }
    protected val navigator by lazy { Navigator(this) }
    /** For keeping the menu is opening or closing. */
    private var isMenuClosed = true
    private val guillotine by lazy {
        GuillotineAnimation.GuillotineBuilder(menu, menu.iv_content_hamburger, iv_content_hamburger)
            .setStartDelay(250)
            .setActionBarViewForAnimation(tb_toolbar)
            .setClosedOnStart(true)
            .setGuillotineListener(object : GuillotineListener {
                override fun onGuillotineClosed() {
                    isMenuClosed = true
                }

                override fun onGuillotineOpened() {
                    isMenuClosed = false
                }
            })
            .build()
    }
    // FIXED(jieyi): 9/23/17 Register it in the parent class that it will be not reflected.
    protected var busEvent = object {
        @Subscribe(tags = [Tag("testTag")])
        fun receive(str: String) {
        }
    }
    private val rootView by lazy { findViewById<ViewGroup>(R.id.root) }

    //region Activity lifecycle
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        RxBus.get().register(busEvent)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
        // HACK(jieyi): 9/17/17 (jieyi): Navigator shouldn't be active by call a variable.
        navigator.activity
        attachMenuView()
    }

    @CallSuper
    override fun onDestroy() {
        RxBus.get().unregister(busEvent)
        super.onDestroy()
    }
    //endregion

    /**
     * Providing the fragment injector([Fragment]) for the searchFragments.
     *
     * @return a [supportFragmentInjector] for children of this fragment.
     */
    override fun supportFragmentInjector(): AndroidInjector<Fragment> = supportFragmentInjector

    /**
     * Providing the fragment injector([android.app.Fragment]) for the searchFragments.
     *
     * @return a [fragmentInjector] for children of this fragment.
     */
    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> = fragmentInjector

    override fun onBackPressed() {
        if (!isMenuClosed) {
            guillotine.close()
            return
        }
        super.onBackPressed()
    }

    protected fun attachMenuView() {
        rootView?.also {
            it.addView(menu)
            guillotine
        }
    }
}