package taiwan.no1.app.ssfm.features.playlist

import android.os.Bundle
import android.support.v7.widget.helper.ItemTouchHelper
import android.transition.TransitionInflater
import com.devrapid.kotlinknifer.recyclerview.WrapContentLinearLayoutManager
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import org.jetbrains.anko.bundleOf
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentPlaylistDetailBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.HELPER_ADD_TO_PLAYLIST
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.PlaylistItemAdapter
import taiwan.no1.app.ssfm.misc.extension.recyclerview.firstFetch
import taiwan.no1.app.ssfm.misc.extension.recyclerview.refreshAndChangeList
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.ItemTouchViewmodelCallback
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.SimpleItemTouchHelperCallback
import taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters.BaseDataBindingAdapter
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
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
        private const val ARG_PARAM_PLAYLIST_TRANSITION: String = "param_playlist_transition_params"

        /**
         * Use this factory method to create a new instance of this fragment using the provided parameters.
         *
         * @return A new instance of [android.app.Fragment] PlaylistDetailFragment.
         */
        fun newInstance(playlist: PlaylistEntity, transition: List<String>) =
            PlaylistDetailFragment().apply {
                arguments = bundleOf(ARG_PARAM_PLAYLIST_OBJECT to playlist,
                                     ARG_PARAM_PLAYLIST_TRANSITION to ArrayList(transition))
                if (transition.isNotEmpty()) {
                    sharedElementEnterTransition = TransitionInflater.from(App.appComponent.context()).inflateTransition(
                        R.transition.change_bound_and_fade)
                    sharedElementReturnTransition = TransitionInflater.from(App.appComponent.context()).inflateTransition(
                        R.transition.change_bound_and_fade)
                }
            }
    }
    //endregion

    @Inject override lateinit var viewModel: PlaylistDetailFragmentViewModel
    @Inject lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private val playlistItemInfo by lazy { DataInfo() }
    private var playlistItemRes = mutableListOf<PlaylistItemEntity>()
    // Get the arguments from the bundle here.
    private val playlist by lazy { arguments.getParcelable<PlaylistEntity>(ARG_PARAM_PLAYLIST_OBJECT) }
    private val transition by lazy { arguments.getStringArrayList(ARG_PARAM_PLAYLIST_TRANSITION) }

    //region Fragment lifecycle
    override fun onDestroyView() {
        (binding?.itemAdapter as BaseDataBindingAdapter<*, *>).detachAll()
        super.onDestroyView()
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            // TODO(jieyi): 11/19/17 Create a map for each elements.
            if (transition.isNotEmpty()) {
                ivAlbumImage.transitionName = transition[0]
                tvName.transitionName = transition[1]
            }
            itemLayoutManager = WrapContentLinearLayoutManager(activity)

            itemAdapter = PlaylistItemAdapter(this@PlaylistDetailFragment,
                                              R.layout.item_music_type_5,
                                              playlistItemRes) { holder, item, index ->
                if (null == holder.binding.avm)
                    holder.binding.avm = RecyclerViewPlaylistDetailViewModel(addPlaylistItemCase,
                                                                             item,
                                                                             index + 1)
                else
                    holder.binding.avm?.setPlaylistItem(item, index + 1)
            }

            val callback = SimpleItemTouchHelperCallback(itemAdapter as PlaylistItemAdapter, vmItemTouchCallback)
            ItemTouchHelper(callback).attachToRecyclerView(recyclerView)
        }
        viewModel.attachPlaylistInfo(playlist)
        playlistItemInfo.firstFetch { info ->
            viewModel.fetchPlaylistItems(playlist.id) {
                playlistItemRes.refreshAndChangeList(it, 0, binding?.itemAdapter as PlaylistItemAdapter, info)
            }
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_playlist_detail
    //endregion

    /**
     * @param playlistItem
     *
     * @event_from [taiwan.no1.app.ssfm.features.playlist.RecyclerViewPlaylistDetailViewModel.trackOnClick]
     */
    @Subscribe(tags = [(Tag(HELPER_ADD_TO_PLAYLIST))])
    fun addToPlaylist(playlistItem: PlaylistItemEntity) {
        playerHelper.addToPlaylist(playlistItem, playlistItemRes, this.javaClass.name)
    }

    private val vmItemTouchCallback = object : ItemTouchViewmodelCallback {
        override fun onItemDismiss(position: Int, direction: Int) {
            playlistItemRes[position].let { deletedItem ->
                viewModel.deleteItem(deletedItem) { if (it) playlistItemRes.remove(deletedItem) }
            }
        }
    }
}