package taiwan.no1.app.ssfm.models.entities

import android.support.annotation.DrawableRes
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewtype.ExpandableViewTypeFactory
import taiwan.no1.app.ssfm.mvvm.models.IExpandVisitable

/**
 * The entity of the main item of the preference setting with a toggle button.
 *
 * @author  jieyi
 * @since   9/8/17
 */
data class PreferenceToggleEntity(val title: String,
                                  var isToggle: Boolean,
                                  @DrawableRes val icon: Int = -1,
                                  override var childItemList: List<IExpandVisitable> = mutableListOf(),
                                  override var isExpanded: Boolean = false): IExpandVisitable {
    override fun type(typeFactory: ExpandableViewTypeFactory): Int = typeFactory.type(this)
}