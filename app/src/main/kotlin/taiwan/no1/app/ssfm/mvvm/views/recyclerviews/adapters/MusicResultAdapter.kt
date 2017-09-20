package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.viewmodels.MusicResultViewModel

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class MusicResultAdapter(var res: MutableList<InfoBean>,
                         val context: Context):
    RecyclerView.Adapter<MusicResultAdapter.BindingHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingHolder {
        val binding = DataBindingUtil.inflate<ItemSearchMusicType1Binding>(LayoutInflater.from(parent.context),
            R.layout.item_search_music_type_1, parent, false)
        return BindingHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingHolder, position: Int) {
        val binding = holder.binding
        binding.avm = MusicResultViewModel(res[position], context)
    }

    override fun getItemCount(): Int = res.size

    class BindingHolder(val binding: ItemSearchMusicType1Binding): RecyclerView.ViewHolder(binding.root)
}