package taiwan.no1.app.ssfm.models.entities

import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewtype.ExpandableViewTypeFactory
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable

/**
 * The entity of the sub-item of the options in preference setting.
 *
 * @author  jieyi
 * @since   9/8/17
 */
data class PreferenceOptionEntity(val title: String,
                                  override var childItemList: List<IExpandVisitable> = mutableListOf(),
                                  override var isExpanded: Boolean = false): IExpandVisitable {
    override fun type(typeFactory: ExpandableViewTypeFactory): Int = typeFactory.type(this)
}