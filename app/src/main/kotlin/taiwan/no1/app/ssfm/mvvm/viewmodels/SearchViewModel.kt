package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.app.Activity
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logd
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.observer
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.misc.extension.hideSoftKeyboard
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.SearchMusicCase.RequestValue

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class SearchViewModel(activity: Activity, private val usecase: BaseUsecase<SearchMusicEntity, RequestValue>):
    BaseViewModel(activity) {
    /** Menu Title */
    val title = ObservableField<String>()
    /** Check search view is clicked or un-clicked */
    val isSearching = ObservableField<Boolean>()

    init {
        title.set(activity.getString(R.string.menu_search))
    }

    /**
     * The action of closing the search view.
     */
    fun closeSearchView(): Boolean {
        isSearching.set(false)
        return false
    }

    /**
     * The action of opening the search view.
     *
     * @param view
     */
    fun openSearchView(view: View) {
        isSearching.set(true)
    }

    /**
     * The action of submitting the search query.
     *
     * @param query the query of song's or singer's name for searching a music.
     */
    fun querySubmit(query: String): Boolean {
        context.hideSoftKeyboard()
        usecase.apply { parameters = SearchMusicCase.RequestValue(query) }.
            execute(observer<SearchMusicEntity>().onNext {
                logw(it)
            }.onComplete {
                logd()
            }.onError {
                loge(it)
            })
        return true
    }

    /**
     * The action of the search query's newText is changed.
     *
     * @param newText inputting by the user in the search view.
     */
    fun textChanged(newText: String): Boolean {
        return true
    }
}