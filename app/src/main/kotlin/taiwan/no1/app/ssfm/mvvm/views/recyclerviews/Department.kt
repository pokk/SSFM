package taiwan.no1.app.ssfm.mvvm.views.recyclerviews

import taiwan.no1.app.ssfm.mvvm.models.IVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.IViewTypeFactory

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/3/17
 */
data class Department(val name: String,
                      override var childItemList: List<*>,
                      override var isExpandable: Boolean): IVisitable {
    override fun type(typeFactory: IViewTypeFactory): Int = typeFactory.type(this)
}