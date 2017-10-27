package taiwan.no1.app.ssfm.pattern.strategy

import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   10/27/17
 */
interface IInitialRecyclerViewStrategy {
    fun initLayoutManager()
    fun initAdapter()
    fun initDecoration()
    fun initLoadMore(fetchFun: (page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit) -> Unit)
}