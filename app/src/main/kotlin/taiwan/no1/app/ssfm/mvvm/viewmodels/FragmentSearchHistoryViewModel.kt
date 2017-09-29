package taiwan.no1.app.ssfm.mvvm.viewmodels

import com.devrapid.kotlinknifer.observer
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.mvvm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetKeywordHistoriesCase
import taiwan.no1.app.ssfm.mvvm.views.fragments.SearchHistoryFragment

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchHistoryViewModel(private val fragment: SearchHistoryFragment,
                                     private val getHistoriesUsecase: BaseUsecase<List<KeywordEntity>, GetKeywordHistoriesCase.RequestValue>):
    BaseViewModel(fragment.activity) {
    //region Lifecycle
    override fun onAttach() {
        RxBus.get().register(this)
    }

    override fun onDetach() {
        RxBus.get().unregister(this)
    }
    //endregion

    fun fetchHistoryList(callback: (List<KeywordEntity>) -> Unit) {
        getHistoriesUsecase.execute(observer { callback(it) })
    }
}