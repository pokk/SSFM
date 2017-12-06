package taiwan.no1.app.ssfm.functions.preference

import android.databinding.ObservableField
import android.view.View
import com.devrapid.adaptiverecyclerview.AdaptiveViewHolder
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.PreferenceOptionEntity

/**
 * A [AdaptiveViewHolder] for controlling the sub-layout of a main preference item.
 *
 * @author  jieyi
 * @since   9/8/17
 */
class PreferenceOptionViewModel(entity: PreferenceOptionEntity) : BaseViewModel() {
    val attributes by lazy { ObservableField<String>() }

    init {
        attributes.set(entity.title)
    }

    fun onClick(view: View) {
        // Send the title to upper layer item.
    }
}