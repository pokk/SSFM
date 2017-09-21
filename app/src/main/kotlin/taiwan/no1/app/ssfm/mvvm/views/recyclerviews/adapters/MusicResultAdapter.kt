package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders.BindingHolder

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class MusicResultAdapter<BH: ViewDataBinding>(@LayoutRes val layout: Int,
                                              private val listener: (holder: BindingHolder<BH>, position: Int) -> Unit):
    RecyclerView.Adapter<BindingHolder<BH>>() {

    var dataSize = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<BH> =
        DataBindingUtil.inflate<BH>(LayoutInflater.from(parent.context),
            R.layout.item_search_music_type_1, parent, false).let { BindingHolder(it) }

    override fun onBindViewHolder(holder: BindingHolder<BH>, position: Int) = listener(holder, position)
//        val binding = holder.binding
//        binding.avm = MusicResultViewModel(res[position], context)

    override fun getItemCount(): Int = dataSize
}