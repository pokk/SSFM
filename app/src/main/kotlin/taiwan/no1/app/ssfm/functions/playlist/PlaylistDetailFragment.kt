package taiwan.no1.app.ssfm.functions.playlist

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentPlaylistDetailBinding
import taiwan.no1.app.ssfm.databinding.ItemMusicType5Binding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.ItemTouchViewmodelCallback
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.SimpleItemTouchHelperCallback
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import javax.inject.Inject


/**
 * @author  jieyi
 * @since   11/14/17
 */
class PlaylistDetailFragment : AdvancedFragment<PlaylistDetailFragmentViewModel, FragmentPlaylistDetailBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_PLAYLIST_OBJECT: String = "param_playlist_object"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] PlaylistDetailFragment.
         */
        fun newInstance(id: PlaylistEntity) = PlaylistDetailFragment().also {
            it.arguments = Bundle().apply {
                putParcelable(ARG_PARAM_PLAYLIST_OBJECT, id)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: PlaylistDetailFragmentViewModel
    private val playlistItemInfo by lazy { DataInfo() }
    private var playlistItemRes = mutableListOf<BaseEntity>(PlaylistItemEntity(),
        PlaylistItemEntity(),
        PlaylistItemEntity(),
        PlaylistItemEntity(),
        PlaylistItemEntity(),
        PlaylistItemEntity())
    // Get the arguments from the bundle here.
    private val playlist by lazy { this.arguments.getParcelable<PlaylistEntity>(ARG_PARAM_PLAYLIST_OBJECT) }

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            itemLayoutManager = WrapContentLinearLayoutManager(activity)

            itemAdapter = BaseDataBindingAdapter<ItemMusicType5Binding, BaseEntity>(R.layout.item_music_type_5,
                playlistItemRes) { holder, item ->
                holder.binding.avm = RecyclerViewPlaylistDetailViewModel(item).apply {
                    onAttach(this@PlaylistDetailFragment)
                }
            }

            val callback = SimpleItemTouchHelperCallback(itemAdapter as BaseDataBindingAdapter<ItemMusicType5Binding, BaseEntity>,
                vmItemTouchCallback)
            ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
        }
        viewModel.attachPlaylistInfo(playlist)
        if (-1 < playlist.id) {
            playlistItemInfo.firstFetch { info ->
                viewModel.fetchPlaylistItems(playlist.id) { logw(it) }
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_playlist_detail
    //endregion

    private val vmItemTouchCallback = object : ItemTouchViewmodelCallback {
        override fun onItemDismiss(position: Int, direction: Int) {
            (playlistItemRes[position] as PlaylistItemEntity).let { deletedItem ->
                viewModel.deleteItem(deletedItem) { if (it) playlistItemRes.remove(deletedItem) }
            }
        }
    }
}