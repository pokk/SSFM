package taiwan.no1.app.ssfm.functions.login

import android.app.Activity
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityLoginBinding
import taiwan.no1.app.ssfm.misc.widgets.MenuItem
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

    //region Base activity implement
    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_login)
    //endregion
}