package taiwan.no1.app.ssfm.mvvm.views.recyclerviews

import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/3/17
 */
data class Company(val name: String,
                   override var childItemList: List<*>,
                   override var isExpandable: Boolean = true): IExpandVisitable {
    override fun type(typeFactory: ExpandableViewTypeFactory): Int = typeFactory.type(this)
}