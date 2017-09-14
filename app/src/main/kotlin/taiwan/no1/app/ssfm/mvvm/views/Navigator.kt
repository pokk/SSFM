package taiwan.no1.app.ssfm.mvvm.views

import android.app.Activity
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_chart
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_favorite
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_home
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_playlist
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_search
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_setting
import kotlinx.android.synthetic.main.part_toolbar_menu.view.iv_content_hamburger
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.startActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.IndexActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PlayMainActivity

/**
 * @author  jieyi
 * @since   9/15/17
 */
class Navigator(val activity: Activity) {
    private val menu by lazy { (this.activity as BaseActivity).menu }

    init {
        this.setMenuListener()
    }

    private fun setMenuListener() {
        this.menu.tv_menu_home.onClick {
            // TODO(jieyi): 9/15/17 delay 250 ms then perform click.
            this@Navigator.menu.iv_content_hamburger.performClick()
            this@Navigator.activity.startActivity<PlayMainActivity>()
        }
        this.menu.tv_menu_playlist.onClick {
            this@Navigator.menu.iv_content_hamburger.performClick()
            this@Navigator.activity.startActivity<IndexActivity>()
        }
        this.menu.tv_menu_favorite.onClick {
        }
        this.menu.tv_menu_search.onClick {
        }
        this.menu.tv_menu_chart.onClick {
        }
        this.menu.tv_menu_setting.onClick {
        }
    }
}