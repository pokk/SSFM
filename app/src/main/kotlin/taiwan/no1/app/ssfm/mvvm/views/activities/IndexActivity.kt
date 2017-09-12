package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityIndexBinding
import taiwan.no1.app.ssfm.misc.widgets.MenuItem
import taiwan.no1.app.ssfm.misc.widgets.SideMenu
import taiwan.no1.app.ssfm.mvvm.viewmodels.IndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 * @author  jieyi
 * @since   9/13/17
 */
class IndexActivity: AdvancedActivity<IndexViewModel, ActivityIndexBinding>() {
    @Inject override lateinit var viewModel: IndexViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val menu = SideMenu(this).apply {
            background = getDrawable(R.drawable.bkg_menu)
            attachActivity(this@IndexActivity)
        }
        // create menu items;
        val titles = this.resources.getStringArray(R.array.side_menu)
        val icons = this.resources.getIntArray(R.array.ic_side_menu)

        for (i in titles.indices) {
            val item = MenuItem(this, icons[i], titles[i])
            menu.addMenuItem(item)
        }

        menu.openMenu()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_index)
}