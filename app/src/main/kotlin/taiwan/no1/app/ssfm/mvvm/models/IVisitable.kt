package taiwan.no1.app.ssfm.mvvm.models

import taiwan.no1.app.ssfm.mvvm.view.viewholder.IViewTypeFactory


/**
 * @author  Jieyi
 * @since   1/7/17
 */

interface IVisitable {
    fun type(typeFactory: IViewTypeFactory): Int
}