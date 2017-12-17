package taiwan.no1.app.ssfm.features.preference

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableInt
import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.PreferenceToggleEntity

/**
 * A [AdaptiveViewHolder] for controlling the main preference items with toggle button.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceToggleViewModel(private val entity: PreferenceToggleEntity) : BaseViewModel() {
    val title by lazy { ObservableField<String>() }
    val selected by lazy { ObservableBoolean() }
    val icon by lazy { ObservableInt() }
    var setBack: ((entityName: String, checked: Boolean) -> Unit)? = null

    init {
        title.set(entity.title)
        icon.set(entity.icon)
        selected.set(entity.isToggle)
    }

    fun onCheckedChange(view: View, checked: Boolean) {
        entity.isToggle = checked
        setBack?.invoke(entity.title, checked)
    }
}
