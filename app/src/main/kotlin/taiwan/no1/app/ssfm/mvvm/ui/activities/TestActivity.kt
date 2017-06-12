package taiwan.no1.app.ssfm.mvvm.ui.activities

import android.app.Activity
import android.os.Bundle
import com.devrapid.kotlinknifer.logd
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.MenuItem
import taiwan.no1.app.ssfm.customized.SideMenu


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

        for (index in 0..(icons.length() - 1)) run {
            logd(icons.getResourceId(index, -1), titles.getString(index))
            this.menu.addMenuItem(MenuItem(this, icons.getResourceId(index, -1), titles.getString(index)))
        }

        icons.recycle()
        titles.recycle()
    }

    override fun onResume() {
        super.onResume()

        this.menu.menuListener.openMenu { logd("open the menu!!") }.closeMenu { logd("close the menu!!") }
    }
}