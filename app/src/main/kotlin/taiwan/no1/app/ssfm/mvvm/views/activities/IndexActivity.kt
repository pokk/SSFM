package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import com.yalantis.guillotine.animation.GuillotineAnimation
import kotlinx.android.synthetic.main.activity_index.root
import kotlinx.android.synthetic.main.part_toolbar_menu.iv_content_hamburger
import kotlinx.android.synthetic.main.part_toolbar_menu.tb_toolbar
import kotlinx.android.synthetic.main.part_toolbar_menu.view.iv_content_hamburger
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivityIndexBinding
import taiwan.no1.app.ssfm.misc.widgets.MenuItem
import taiwan.no1.app.ssfm.mvvm.viewmodels.IndexViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject

/**
 * @author  jieyi
 * @since   9/13/17
 */
class IndexActivity: AdvancedActivity<IndexViewModel, ActivityIndexBinding>() {
    @Inject override lateinit var viewModel: IndexViewModel
    private val menuItems by lazy {
        // create menu items;
        val titles = this.resources.getStringArray(R.array.side_menu)
        val icons = this.resources.obtainTypedArray(R.array.ic_side_menu)

        List(titles.size) { MenuItem(this, icons.getResourceId(it, -1), titles[it]) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val guillotineMenu = LayoutInflater.from(this).inflate(R.layout.page_menu, null)
        root.addView(guillotineMenu)

        GuillotineAnimation.GuillotineBuilder(guillotineMenu,
            guillotineMenu.iv_content_hamburger, iv_content_hamburger)
            .setStartDelay(250)
            .setActionBarViewForAnimation(tb_toolbar)
            .setClosedOnStart(true)
            .build()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_index)
}