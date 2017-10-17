package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.observer
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
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
    lateinit var deleteItemListener: (entity: KeywordEntity, isSuccess: Boolean) -> Unit
    val keyword by lazy { ObservableField<String>(entity.keyword) }

    fun deleteHistoryClick(view: View) {
        deleteHistoriesUsecase.execute(RequestValue(keyword.get()),
            observer = observer { deleteItemListener(entity, it) })
    }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun selectHistoryItem(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, entity.keyword)
    }
}