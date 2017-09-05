package taiwan.no1.app.ssfm.mvvm.models

import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.IViewTypeFactory


/**
 * @author  Jieyi
 * @since   1/7/17
 */

interface IVisitable {
    var childItemList: List<*>
    var isExpandable: Boolean

    fun type(typeFactory: IViewTypeFactory): Int
}