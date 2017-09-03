package taiwan.no1.app.ssfm.mvvm.views.recyclerview

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
data class Company(val name: String,
                   override var childItemList: List<*>,
                   override var isExpandable: Boolean = true): ExpandableItem