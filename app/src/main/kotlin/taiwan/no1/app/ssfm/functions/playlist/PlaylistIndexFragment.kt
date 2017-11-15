package taiwan.no1.app.ssfm.functions.playlist

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentMylistIndexBinding
import taiwan.no1.app.ssfm.databinding.ItemMusicType3Binding
import taiwan.no1.app.ssfm.databinding.ItemPlaylistType1Binding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.PlaylistAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecentlyAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
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
        fun newInstance() = PlaylistIndexFragment().also {
            it.arguments = Bundle().also {}
        }
    }
    //endregion

    @Inject override lateinit var viewModel: PlaylistIndexFragmentViewModel
    private val playlistInfo by lazy { DataInfo() }
    private val recentlyPlayedInfo by lazy { DataInfo() }
    private var playlistRes = mutableListOf<BaseEntity>()
    private var recentlyPlayedRes = mutableListOf<BaseEntity>()

    override fun onResume() {
        super.onResume()
        playlistRes.clear()
        recentlyPlayedRes.clear()
    }

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            playlistLayoutManager = WrapContentLinearLayoutManager(activity)
            recentlyLayoutManager = WrapContentLinearLayoutManager(activity)

            playlistAdapter = BaseDataBindingAdapter<ItemPlaylistType1Binding, BaseEntity>(R.layout.item_playlist_type_1,
                playlistRes) { holder, item ->
                holder.binding.avm = RecyclerViewPlaylistViewModel(item).apply {
                    onAttach(this@PlaylistIndexFragment)
                }
            }
            recentlyAdapter = BaseDataBindingAdapter<ItemMusicType3Binding, BaseEntity>(R.layout.item_music_type_3,
                recentlyPlayedRes) { holder, item ->
                holder.binding.avm = RecyclerViewRecentlyPlaylistViewModel(item).apply {
                    onAttach(this@PlaylistIndexFragment)
                }
            }
        }
        // First time showing this fragment.
        viewModel.fetchPlaylistAndRecently({
            playlistRes.refreshAndChangeList(it, 1, binding?.playlistAdapter as PlaylistAdapter, playlistInfo)
        }, {
            recentlyPlayedRes.refreshAndChangeList(it,
                1, binding?.recentlyAdapter as RecentlyAdapter, recentlyPlayedInfo)
        })
    }

    override fun provideInflateView(): Int = R.layout.fragment_mylist_index
    //endregion
}