package taiwan.no1.app.ssfm.functions.search

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.observer
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.Constant.VIEWMODEL_PARAMS_KEYWORD
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.DeleteSearchHistoryCase
import taiwan.no1.app.ssfm.models.usecases.RemoveKeywordHistoriesUsecase

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchHistoryViewModel(private val keywordEntity: BaseEntity,
                                         private val context: Context,
                                         private val deleteHistoriesUsecase: DeleteSearchHistoryCase) : BaseObservable() {
    lateinit var deleteItemListener: (entity: KeywordEntity, isSuccess: Boolean) -> Unit
    val keyword by lazy { ObservableField<String>() }

    init {
        (keywordEntity as KeywordEntity).let {
            keyword.set(it.keyword)
        }
    }

    fun deleteHistoryClick(view: View) {
        deleteHistoriesUsecase.execute(RemoveKeywordHistoriesUsecase.RequestValue(keyword.get()),
            observer = observer { deleteItemListener((keywordEntity as KeywordEntity), it) })
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun selectHistoryItem(view: View) {
        keywordEntity as KeywordEntity
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY,
            hashMapOf(VIEWMODEL_PARAMS_KEYWORD to keywordEntity.keyword))
    }
}