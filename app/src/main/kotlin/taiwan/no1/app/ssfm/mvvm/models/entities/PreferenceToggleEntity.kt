package taiwan.no1.app.ssfm.mvvm.models.entities

import android.support.annotation.DrawableRes
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype.ExpandableViewTypeFactory

/**
 *
 * @author  jieyi
 * @since   9/8/17
 */
data class PreferenceToggleEntity(val title: String,
                                  var isToggle: Boolean,
                                  @DrawableRes val icon: Int = -1,
                                  override var childItemList: List<IExpandVisitable> = mutableListOf(),
                                  override var isExpandable: Boolean = childItemList.isNotEmpty(),
                                  override var isExpanded: Boolean = false): IExpandVisitable {
    override fun type(typeFactory: ExpandableViewTypeFactory): Int = typeFactory.type(this)
}