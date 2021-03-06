package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewtype

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import com.devrapid.adaptiverecyclerview.ViewTypeFactory
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.models.entities.PreferenceOptionEntity
import taiwan.no1.app.ssfm.models.entities.PreferenceToggleEntity

/**
 * A creating factory for multiple type of view-holder.
 *
 * @author  jieyi
 * @since   9/6/17
 */
class ExpandableViewTypeFactory : ViewTypeFactory() {
    override var transformMap: MutableMap<Int, Pair<Int, (View) -> ViewHolder>> = mutableMapOf(
        3 to Pair(R.layout.item_preference_first_layer_title, { _ -> TODO() }),
        4 to Pair(R.layout.item_preference_first_layer_toggle, { _ -> TODO() }),
        5 to Pair(R.layout.item_preference_second_layer_title, { _ -> TODO() }))

    // NOTE(jieyi): 9/5/17 Add the new type here.
    fun type(entity: PreferenceEntity): Int = 3

    fun type(entity: PreferenceToggleEntity): Int = 4
    fun type(entity: PreferenceOptionEntity): Int = 5
}