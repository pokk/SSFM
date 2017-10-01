package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.observer
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.RemoveKeywordHistoriesCase.RequestValue

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchHistoryViewModel(private val entity: KeywordEntity,
                                         private val context: Context,
                                         private val deleteHistoriesUsecase: BaseUsecase<Boolean, RemoveKeywordHistoriesCase.RequestValue>):
    BaseObservable() {
    val keyword = ObservableField<String>(entity.keyword)
    lateinit var deleteItemListener: (entity: KeywordEntity, isSuccess: Boolean) -> Unit

    fun deleteHistoryClick(view: View) {
        deleteHistoriesUsecase.execute(RequestValue(keyword.get()), observer { deleteItemListener(entity, it) })
    }
}