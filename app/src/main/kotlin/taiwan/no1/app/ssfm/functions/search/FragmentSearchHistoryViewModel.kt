package taiwan.no1.app.ssfm.functions.search

import android.content.Context
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
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
        lifecycleProvider.execute(getHistoriesUsecase) { onNext(callback) }
    }
}