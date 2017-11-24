package taiwan.no1.app.ssfm.models.entities

import android.support.annotation.DrawableRes
import io.reactivex.Observer
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewtype.ExpandableViewTypeFactory
import taiwan.no1.app.ssfm.models.IExpandVisitable

/**
 * The entity of the main item of the preference setting.
 *
 * @author  jieyi
 * @since   9/8/17
 */
data class PreferenceEntity(val title: String,
                            var attributes: String,
                            @DrawableRes
                            val icon: Int = -1,
                            var observer: Observer<String>? = null,
                            override var childItemList: List<IExpandVisitable> = mutableListOf(),
                            override var isExpanded: Boolean = false) : IExpandVisitable {
    override fun type(typeFactory: ExpandableViewTypeFactory): Int = typeFactory.type(this)
}