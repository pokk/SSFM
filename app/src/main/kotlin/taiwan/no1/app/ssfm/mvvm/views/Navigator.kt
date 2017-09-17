package taiwan.no1.app.ssfm.mvvm.views

import android.view.View
import com.devrapid.kotlinknifer.observer
import com.jakewharton.rxbinding2.view.clicks
import com.trello.rxlifecycle2.components.RxActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_chart
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_favorite
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_home
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_login
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_playlist
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_search
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_setting
import kotlinx.android.synthetic.main.part_toolbar_menu.view.iv_content_hamburger
import org.jetbrains.anko.startActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.ChartActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.IndexActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PlayMainActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PlaylistActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PreferenceActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.SearchActivity
import java.util.concurrent.TimeUnit.MILLISECONDS

/**
 * @author  jieyi
 * @since   9/15/17
 */
class Navigator(val activity: RxActivity) {
    private val menu by lazy { (activity as BaseActivity).menu }

    init {
        setMenuListener()
    }

    private fun setMenuListener() {
        menu.tv_menu_home.wrapClick().subscribe(transferClick { activity.startActivity<PlayMainActivity>() })
        menu.tv_menu_playlist.wrapClick().subscribe(transferClick { activity.startActivity<PlaylistActivity>() })
        menu.tv_menu_favorite.wrapClick().subscribe(transferClick { activity.startActivity<PlaylistActivity>() })
        menu.tv_menu_search.wrapClick().subscribe(transferClick { activity.startActivity<SearchActivity>() })
        menu.tv_menu_chart.wrapClick().subscribe(transferClick { activity.startActivity<ChartActivity>() })
        menu.tv_menu_setting.wrapClick().subscribe(transferClick { activity.startActivity<PreferenceActivity>() })
        menu.tv_menu_login.wrapClick().subscribe(transferClick { activity.startActivity<IndexActivity>() })
    }

    /**
     * Decorate the click event for adding the closing menu function.
     *
     * @return
     */
    private fun View.wrapClick() = clicks().
        bindToLifecycle(activity).
        doOnNext { menu.iv_content_hamburger.performClick() }.
        flatMap { ObservableTimer.timer(500, MILLISECONDS, Schedulers.io()) }.
        observeOn(AndroidSchedulers.mainThread())

    private fun <T> transferClick(onNext: (() -> Unit)? = null): Observer<T> =
        observer<T>().onNext { onNext?.invoke() }.onComplete { activity.finish() }
}