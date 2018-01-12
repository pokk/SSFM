package taiwan.no1.app.ssfm.features.playlist

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentMylistIndexBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.PlaylistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecentlyAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.ItemTouchViewmodelCallback
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.SimpleItemTouchHelperCallback
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import javax.inject.Inject

/**
 * @author  jieyi
 * @since   11/5/17
 */
class PlaylistIndexFragment : AdvancedFragment<PlaylistIndexFragmentViewModel, FragmentMylistIndexBinding>() {
    //region Static initialization
    companion object Factory {
        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] PlaylistIndexFragment.
         */
        fun newInstance() = PlaylistIndexFragment().apply {
            arguments = bundleOf()
        }
    }
    //endregion

    @Inject override lateinit var viewModel: PlaylistIndexFragmentViewModel
    private val playlistInfo by lazy { DataInfo() }
    private val recentlyPlayedInfo by lazy { DataInfo() }
    private var playlistRes = mutableListOf<BaseEntity>()
    private var recentlyPlayedRes = mutableListOf<BaseEntity>()

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        playlistRes.clear()
        recentlyPlayedRes.clear()
    }

    override fun onDestroy() {
        listOf((binding?.playlistAdapter as BaseDataBindingAdapter<*, *>),
               (binding?.recentlyAdapter as BaseDataBindingAdapter<*, *>)).forEach { it.detachAll() }
        super.onDestroy()
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            playlistLayoutManager = WrapContentLinearLayoutManager(activity)
            recentlyLayoutManager = WrapContentLinearLayoutManager(activity)

            playlistAdapter = PlaylistAdapter(this@PlaylistIndexFragment,
                                              R.layout.item_playlist_type_1,
                                              playlistRes) { holder, item, _ ->
                if (null == holder.binding.avm) holder.binding.avm = RecyclerViewPlaylistViewModel(item)
            }
            recentlyAdapter = RecentlyAdapter(this@PlaylistIndexFragment,
                                              R.layout.item_music_type_3,
                                              recentlyPlayedRes) { holder, item, index ->
                if (null == holder.binding.avm)
                    holder.binding.avm = RecyclerViewRecentlyPlaylistViewModel(item, index)
                else
                    holder.binding.avm?.setPlaylistItemAndRefresh(item, index)
            }

            val callback = SimpleItemTouchHelperCallback(playlistAdapter as PlaylistAdapter, vmItemTouchCallback)
            ItemTouchHelper(callback).attachToRecyclerView(rvPlaylist)
        }
        // First time showing this fragment.
        viewModel.fetchPlaylistAndRecently({
                                               playlistRes.refreshAndChangeList(it,
                                                                                1,
                                                                                binding?.playlistAdapter as PlaylistAdapter,
                                                                                playlistInfo)
                                           }, {
                                               recentlyPlayedRes.refreshAndChangeList(it,
                                                                                      1,
                                                                                      binding?.recentlyAdapter as RecentlyAdapter,
                                                                                      recentlyPlayedInfo)
                                           })
    }

    override fun provideInflateView(): Int = R.layout.fragment_mylist_index
    //endregion

    private val vmItemTouchCallback = object : ItemTouchViewmodelCallback {
        override fun onItemDismiss(position: Int, direction: Int) {
            (playlistRes[position] as PlaylistEntity).let { deletedItem ->
                viewModel.deletePlaylist(deletedItem) { if (it) playlistRes.remove(deletedItem) }
            }
        }
    }
}