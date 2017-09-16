package taiwan.no1.app.ssfm.mvvm.views

import android.app.Activity
import android.view.View
import com.devrapid.kotlinknifer.observer
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
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
import taiwan.no1.app.ssfm.mvvm.views.activities.PreferenceActivity
import java.util.concurrent.TimeUnit.MILLISECONDS

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
        this.menu.tv_menu_home.wrapperOnClick {
            Observable.timer(500, MILLISECONDS).
                subscribeOn(Schedulers.io()).
                subscribe(observer<Long>().onNext {
                    this@Navigator.activity.startActivity<IndexActivity>()
                }.onComplete {
                    this@Navigator.activity.finish()
                })
        }
        this.menu.tv_menu_playlist.wrapperOnClick {
            Observable.timer(500, MILLISECONDS).
                subscribeOn(Schedulers.io()).
                subscribe(observer<Long>().onNext {
                    this@Navigator.activity.startActivity<PlayMainActivity>()
                }.onComplete {
                    this@Navigator.activity.finish()
                })
        }
        this.menu.tv_menu_favorite.wrapperOnClick {
        }
        this.menu.tv_menu_search.wrapperOnClick {
        }
        this.menu.tv_menu_chart.wrapperOnClick {
        }
        this.menu.tv_menu_setting.wrapperOnClick {
            Observable.timer(500, MILLISECONDS).
                subscribeOn(Schedulers.io()).
                subscribe(observer<Long>().onNext {
                    this@Navigator.activity.startActivity<PreferenceActivity>()
                }.onComplete {
                    this@Navigator.activity.finish()
                })
        }
    }

    /**
     * Decorate the click event for adding the closing menu function.
     *
     * @param block On click block.
     */
    private fun View.wrapperOnClick(block: (View?) -> Unit) {
        this.onClick {
            // Close the menu first.
            this@Navigator.menu.iv_content_hamburger.performClick()
            block(it)
        }
    }
}