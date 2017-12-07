package taiwan.no1.app.ssfm.functions.preference

import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.recyclerview.MultipleTypeAdapter
import taiwan.no1.app.ssfm.models.entities.PreferenceEntity

/**
 * A [AdaptiveViewHolder] for controlling the main preference items with an option selection.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceItemViewModel(private val adapter: MultipleTypeAdapter,
                              private val position: Int,
                              private val entity: PreferenceEntity) : BaseViewModel() {
    val title by lazy { ObservableField<String>() }
    val icon by lazy { ObservableInt() }
    val selected by lazy { ObservableField<String>() }

    init {
        title.set(entity.title)
        icon.set(entity.icon)
        selected.set(entity.attributes)
    }

    fun onClick(view: View) {
        val newPosition by lazy { adapter.calculateIndex(position) }

        if (adapter.isExpanded(newPosition)) {
            adapter.collapse(position, newPosition)
        }
        else {
            adapter.expand(position, newPosition)
        }
    }
}