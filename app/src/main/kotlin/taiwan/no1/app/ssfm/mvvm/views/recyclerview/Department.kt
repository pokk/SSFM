package taiwan.no1.app.ssfm.mvvm.views.recyclerview

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
data class Department(val name: String,
                      override var childItemList: List<*>,
                      override var isExpandable: Boolean): ExpandableItem