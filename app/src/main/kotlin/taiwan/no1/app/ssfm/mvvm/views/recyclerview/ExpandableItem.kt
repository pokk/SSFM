package taiwan.no1.app.ssfm.mvvm.views.recyclerview

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
interface ExpandableItem {
    var childItemList: List<*>
    var isExpandable: Boolean
}