package taiwan.no1.app.ssfm.features.search

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
class RecyclerViewSearchHistoryViewModel(private var keywordEntity: BaseEntity,
                                         private val context: Context,
                                         private val deleteHistoriesUsecase: DeleteSearchHistoryCase) : BaseObservable() {
    lateinit var deleteItemListener: (entity: KeywordEntity, isSuccess: Boolean) -> Unit
    val keyword by lazy { ObservableField<String>() }

    init {
        refreshView()
    }

    fun setKeywordItem(item: BaseEntity) {
        this.keywordEntity = item
        refreshView()
    }

    fun deleteHistoryClick(view: View) {
        deleteHistoriesUsecase.execute(RemoveKeywordHistoriesUsecase.RequestValue(keyword.get().orEmpty()),
                                       observer = observer { deleteItemListener((keywordEntity as KeywordEntity), it) })
    }

    /**
     * A callback event for clicking a item to list track.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun selectHistoryItem(view: View) {
        (keywordEntity as KeywordEntity).let {
            RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY,
                             hashMapOf(VIEWMODEL_PARAMS_KEYWORD to it.keyword))
        }
    }

    private fun refreshView() {
        (keywordEntity as KeywordEntity).let { keyword.set(it.keyword) }
    }
}
