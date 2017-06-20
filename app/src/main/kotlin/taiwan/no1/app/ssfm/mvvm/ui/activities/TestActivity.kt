package taiwan.no1.app.ssfm.mvvm.ui.activities

//import android.app.Activity
import android.os.Bundle
import com.jakewharton.rxbinding2.widget.changes
import kotlinx.android.synthetic.main.test_customize_view.*
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.customized.SideMenu
import taiwan.no1.app.ssfm.mvvm.ui.BaseActivity
import javax.inject.Inject


/**
 *
 * @author  jieyi
 * @since   6/8/17
 */
class TestActivity: BaseActivity() {
    lateinit var menu: SideMenu
    @Inject
    lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_customize_view)

//        this.menu = SideMenu(this)
//        this.menu.attachActivity(this)
//        this.menu.setMenuBackground(R.drawable.bkg_menu)
//
//        val icons = resources.obtainTypedArray(R.array.ic_side_menu)
//        val titles = resources.obtainTypedArray(R.array.side_menu)
//
//        for (index in 0..(icons.length() - 1)) run {
//            logd(icons.getResourceId(index, -1), titles.getString(index))
//            this.menu.addMenuItem(MenuItem(this, icons.getResourceId(index, -1), titles.getString(index)))
//        }
//
//        logw(name)
//
//        icons.recycle()
//        titles.recycle()
    }

    override fun onResume() {
        super.onResume()

//        this.menu.menuListener.openMenu { logd("open the menu!!") }.closeMenu { logd("close the menu!!") }

        this.sb_radius.changes().subscribe {
            this.civ_icon.borderWidth = it.toFloat()
        }
        this.sb_shadow_radius.changes().subscribe {
            this.civ_icon.shadowRadius = it.toFloat()
        }
    }
}