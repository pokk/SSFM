package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewtype

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import com.devrapid.adaptiverecyclerview.ViewTypeFactory
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceOptionEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.PreferenceToggleEntity
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.PreferenceOptionViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.PreferenceToggleViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.PreferenceViewHolder

/**
 * A creating factory for multiple type of view-holder.
 *
 * @author  jieyi
 * @since   9/6/17
 */
class ExpandableViewTypeFactory: ViewTypeFactory() {
    override var transformMap: MutableMap<Int, Pair<Int, (View) -> ViewHolder>> = mutableMapOf(
        3 to Pair(R.layout.item_preference_first_layer_title, { view -> PreferenceViewHolder(view) }),
        4 to Pair(R.layout.item_preference_first_layer_toggle, { view -> PreferenceToggleViewHolder(view) }),
        5 to Pair(R.layout.item_preference_second_layer_title, { view -> PreferenceOptionViewHolder(view) })
    )

    // NOTE(jieyi): 9/5/17 Add the new type here.
    fun type(entity: PreferenceEntity): Int = 3
    fun type(entity: PreferenceToggleEntity): Int = 4
    fun type(entity: PreferenceOptionEntity): Int = 5
}