package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.viewholders

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * @author  jieyi
 * @since   9/21/17
 */
class BindingHolder<out BH: ViewDataBinding>(val binding: BH): RecyclerView.ViewHolder(binding.root) {
    init {
        binding.executePendingBindings()
    }
}