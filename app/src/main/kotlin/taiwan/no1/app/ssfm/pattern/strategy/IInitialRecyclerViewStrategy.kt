package taiwan.no1.app.ssfm.pattern.strategy

/**
 * @author  jieyi
 * @since   10/27/17
 */
interface IInitialRecyclerViewStrategy {
    fun initLayoutManager()
    fun initAdapter()
    fun initDecoration()
    fun initLoadMore()
}