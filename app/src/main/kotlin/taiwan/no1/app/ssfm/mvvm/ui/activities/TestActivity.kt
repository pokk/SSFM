package taiwan.no1.app.ssfm.mvvm.ui.activities

import android.app.Activity
import android.os.Bundle
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.ui.customize.MenuItem
import taiwan.no1.app.ssfm.mvvm.ui.customize.SideMenu


/**
 *
 * @author  jieyi
 * @since   6/8/17
 */
class TestActivity: Activity() {
    lateinit var menu: SideMenu
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.menu = SideMenu(this)
        this.menu.attachActivity(this)
        this.menu.setMenuBackground(R.drawable.menu_background)

        val titles = arrayOf("Home", "Profile", "Calendar", "Settings")
        val icon = arrayOf(R.drawable.navigation_empty_icon,
            R.drawable.navigation_empty_icon,
            R.drawable.navigation_empty_icon,
            R.drawable.navigation_empty_icon)

        titles.zip(icon, { title, icon ->
            this.menu.addMenuItem(MenuItem(this, icon, title))
        })
    }

    override fun onResume() {
        super.onResume()

        this.menu.menuListener = menuListener
    }

    val menuListener = object: SideMenu.OnMenuListener {
        override fun openMenu() {
            logw("Menu is opened!")
        }

        override fun closeMenu() {
            logw("Menu is closed!")
        }
    }
}