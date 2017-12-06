package taiwan.no1.app.ssfm.functions.preference

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.PreferenceEntity

/**
 * A [AdaptiveViewHolder] for controlling the main preference items with an option selection.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceItemViewModel(entity: PreferenceEntity) : BaseViewModel() {
    val title by lazy { ObservableField<String>() }
    val icon by lazy { ObservableInt() }
    val selected by lazy { ObservableField<String>() }

    init {
        title.set(entity.title)
        icon.set(entity.icon)
        selected.set(entity.attributes)
    }

    fun onClick(view: View) {

    }

//    override fun initView(model: PreferenceEntity, position: Int, adapter: Any) {
//        adapter as ExpandRecyclerViewAdapter
//        val newPosition by lazy { adapter.calculateIndex(position) }
//
//        // Create an observer for the click event of children.
//        if (null == model.observer) {
//            model.observer = observer {
//                itemView.tv_selected.text = it
//                adapter.collapse(position, newPosition)
//                // TODO(jieyi): 9/10/17 Changing app theme processing.
//            }
//        }
//
//        itemView.onClick {
//            if (adapter.isExpanded(newPosition)) {
//                adapter.collapse(position, newPosition)
//            }
//            else {
//                adapter.expand(position, newPosition)
//            }
//        }
}