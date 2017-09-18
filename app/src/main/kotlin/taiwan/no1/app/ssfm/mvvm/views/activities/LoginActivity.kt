package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import android.support.annotation.CallSuper
import kotlinx.android.synthetic.main.part_toolbar_view.tv_menu_title
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityLoginBinding
import taiwan.no1.app.ssfm.misc.widgets.MenuItem
import taiwan.no1.app.ssfm.mvvm.viewmodels.LoginViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 * @author  jieyi
 * @since   9/13/17
 */
class LoginActivity: AdvancedActivity<LoginViewModel, ActivityLoginBinding>() {
    @Inject override lateinit var viewModel: LoginViewModel
    private val menuItems by lazy {
        // create menu items;
        val titles = resources.getStringArray(R.array.side_menu)
        val icons = resources.obtainTypedArray(R.array.ic_side_menu)

        List(titles.size) { MenuItem(this, icons.getResourceId(it, -1), titles[it]) }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tv_menu_title.text = getString(R.string.menu_login)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_login)
}