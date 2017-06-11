package taiwan.no1.app.ssfm.mvvm.ui.activities

import android.app.Activity
import android.os.Bundle
import com.devrapid.kotlinknifer.logd
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
        this.menu.setMenuBackground(R.drawable.bkg_menu)

        val icons = resources.obtainTypedArray(R.array.ic_side_menu)
        val titles = resources.obtainTypedArray(R.array.side_menu)

        logd(icons.length(), titles.length())

        for (index in 0..(icons.length() - 1)) run {
            logd(icons.getResourceId(index, -1), titles.getString(index))
            this.menu.addMenuItem(MenuItem(this, icons.getResourceId(index, -1), titles.getString(index)))
        }

        icons.recycle()
        titles.recycle()
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