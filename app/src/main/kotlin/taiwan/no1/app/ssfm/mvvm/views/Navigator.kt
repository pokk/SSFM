package taiwan.no1.app.ssfm.mvvm.views

import android.app.Activity
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.observer
import com.jakewharton.rxbinding2.view.clicks
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_chart
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_favorite
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_home
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_playlist
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_search
import kotlinx.android.synthetic.main.page_menu.view.tv_menu_setting
import kotlinx.android.synthetic.main.part_toolbar_menu.view.iv_content_hamburger
import layout.PlaylistActivity
import org.jetbrains.anko.startActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.ChartActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PlayMainActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.PreferenceActivity
import taiwan.no1.app.ssfm.mvvm.views.activities.SearchActivity
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
        this.menu.tv_menu_home.clicks().
            doOnNext { this@Navigator.menu.iv_content_hamburger.performClick() }.
            flatMap { ObservableTimer.timer(500, MILLISECONDS, Schedulers.io()) }.
            observeOn(AndroidSchedulers.mainThread()).
            subscribe { this@Navigator.activity.startActivity<PlayMainActivity>() }
        this.menu.tv_menu_playlist.clicks().
            doOnNext { this@Navigator.menu.iv_content_hamburger.performClick() }.
            flatMap { ObservableTimer.timer(500, MILLISECONDS, Schedulers.io()) }.
            observeOn(AndroidSchedulers.mainThread()).
            subscribe { this@Navigator.activity.startActivity<PlaylistActivity>() }
        this.menu.tv_menu_favorite.clicks().
            doOnNext { this@Navigator.menu.iv_content_hamburger.performClick() }.
            flatMap { ObservableTimer.timer(500, MILLISECONDS, Schedulers.io()) }.
            observeOn(AndroidSchedulers.mainThread()).
            subscribe { this@Navigator.activity.startActivity<PlayMainActivity>() }
        this.menu.tv_menu_search.clicks().wrapperOnClick().subscribe(this.clickResponse {
            this@Navigator.activity.startActivity<SearchActivity>()
        })
        this.menu.tv_menu_chart.clicks().wrapperOnClick().subscribe(this.clickResponse {
            this@Navigator.activity.startActivity<ChartActivity>()
        })
        this.menu.tv_menu_setting.clicks().wrapperOnClick().subscribe(this.clickResponse {
            this@Navigator.activity.startActivity<PreferenceActivity>()
        })
    }

    /**
     * Decorate the click event for adding the closing menu function.
     *
     * @return
     */
    private fun Observable<Unit>.wrapperOnClick(): Observable<Long> =
        doOnNext { this@Navigator.menu.iv_content_hamburger.performClick() }.
            flatMap { ObservableTimer.timer(500, MILLISECONDS, Schedulers.io()) }.
            // Close the menu first.
            observeOn(AndroidSchedulers.mainThread()).
            doOnSubscribe { it.dispose() }

    private fun clickResponse(onNext: (() -> Unit)? = null): Observer<Long> =
        observer<Long>().
            onNext { onNext?.invoke() }.
            onComplete { this@Navigator.activity.finish() }.
            onSubscribe {
                logw("?????????", it)
                it.dispose()
            }

}