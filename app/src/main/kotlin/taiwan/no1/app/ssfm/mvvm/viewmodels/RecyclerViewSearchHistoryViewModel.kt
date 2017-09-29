package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchHistoryViewModel(val res: KeywordEntity, context: Context): BaseObservable() {
    val keyword = ObservableField<String>(res.keyword)

    fun deleteHistoryClick(view: View) {
    }
}