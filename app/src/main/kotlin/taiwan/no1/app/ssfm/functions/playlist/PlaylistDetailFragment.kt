package taiwan.no1.app.ssfm.functions.playlist

import android.os.Bundle
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentPlaylistDetailBinding
import taiwan.no1.app.ssfm.databinding.ItemMusicType5Binding
import taiwan.no1.app.ssfm.functions.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
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
        private const val ARG_PARAM_PLAYLIST_ID: String = "param_playlist_id"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] PlaylistDetailFragment.
         */
        fun newInstance(id: Int) = PlaylistDetailFragment().also {
            it.arguments = Bundle().apply {
                putInt(ARG_PARAM_PLAYLIST_ID, id)
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: PlaylistDetailFragmentViewModel
    private val playlistItemInfo by lazy { DataInfo() }
    private var playlistItemRes = mutableListOf<BaseEntity>()
    // Get the arguments from the bundle here.
    private val playlistId: Int by lazy { this.arguments.getInt(ARG_PARAM_PLAYLIST_ID) }

    //region Base fragment implement
    override fun init(savedInstanceState: Bundle?) {
        binding?.apply {
            itemLayoutManager = WrapContentLinearLayoutManager(activity)

            itemAdapter = BaseDataBindingAdapter<ItemMusicType5Binding, BaseEntity>(R.layout.item_music_type_5,
                playlistItemRes) { holder, item ->
                holder.binding.avm = RecyclerViewPlaylistViewModel(item).apply {
                    onAttach(this@PlaylistDetailFragment)
                }
            }
        }
        // First time showing this fragment.
        playlistItemInfo.firstFetch { info ->
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_mylist_index
    //endregion
}