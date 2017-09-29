package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import com.devrapid.kotlinknifer.observer
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchHistoryViewModel(private val context: Context,
                                     private val getHistoriesUsecase: BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>):
    BaseViewModel() {
    fun fetchHistoryList(callback: (List<KeywordEntity>) -> Unit) {
        getHistoriesUsecase.execute(observer { callback(it) })
    }
}