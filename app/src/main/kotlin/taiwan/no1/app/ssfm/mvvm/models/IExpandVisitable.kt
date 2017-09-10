package taiwan.no1.app.ssfm.mvvm.models

import com.devrapid.adaptiverecyclerview.IVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 *
 *
 * @author  jieyi
 * @since   9/6/17
 */
interface IExpandVisitable: IVisitable<ExpandableViewTypeFactory> {
    var childItemList: List<IExpandVisitable>
    var isExpanded: Boolean
}