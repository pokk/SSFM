package taiwan.no1.app.ssfm.features.search

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import org.jetbrains.anko.act
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchResultBinding
import taiwan.no1.app.ssfm.features.base.AdvancedFragment
import taiwan.no1.app.ssfm.misc.constants.Constant.SPECIAL_NUMBER
import taiwan.no1.app.ssfm.misc.extension.gColor
import taiwan.no1.app.ssfm.misc.extension.recyclerview.DataInfo
import taiwan.no1.app.ssfm.misc.extension.recyclerview.RecyclerViewScrollCallback
import taiwan.no1.app.ssfm.misc.extension.recyclerview.SearchHistoryAdapter
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import javax.inject.Inject
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment : AdvancedFragment<SearchResultFragmentViewModel, FragmentSearchResultBinding>() {
    //region Static initialization
    companion object Factory {
        // The key name of the fragment initialization parameters.
        private const val ARG_PARAM_KEYWORD: String = "param_music_keyword"
        private const val ARG_PARAM_BACKGROUND_IMAGE_URL: String = "param_background_image_url"
        private const val ARG_PARAM_FOREGROUND_BLUR_COLOR: String = "param_foreground_blur_color"

        /**
         * Use this factory method to create a new instance of this fragment using the
         * provided parameters.
         *
         * @return A new instance of [android.app.Fragment] SearchResultFragment.
         */
        fun newInstance(keyword: String = "",
                        imageUrl: String = "",
                        fgColor: Int = gColor(R.color.colorTransparent)) = SearchResultFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM_KEYWORD, keyword)
                putString(ARG_PARAM_BACKGROUND_IMAGE_URL, imageUrl)
                putInt(ARG_PARAM_FOREGROUND_BLUR_COLOR,
                    fgColor.takeIf { SPECIAL_NUMBER != it }.let { it } ?: gColor(R.color.colorTransparent))
            }
        }
    }
    //endregion

    @Inject override lateinit var viewModel: SearchResultFragmentViewModel
    @field:[Inject Named("add_playlist_item")] lateinit var addPlaylistItemCase: AddPlaylistItemCase
    private var res = mutableListOf<BaseEntity>()
    private val resInfo by lazy { DataInfo() }
    // Get the arguments from the bundle here.
    private val keyword by lazy { arguments.getString(ARG_PARAM_KEYWORD) }
    private val bkgImageUrl by lazy { arguments.getString(ARG_PARAM_BACKGROUND_IMAGE_URL) }
    private val fgFogColor by lazy { arguments.getInt(ARG_PARAM_FOREGROUND_BLUR_COLOR) }

    //region Fragment lifecycle
    override fun onResume() {
        super.onResume()
        // Due to this object is kept by `SearchActivity`, this list need to be cleared every time.
        res.clear()
        resInfo.isLoading = true
        viewModel.sendSearchRequest(keyword, resultCallback = updateListInfo)
    }
    //endregion

    //region Base fragment implement
    override fun rendered(savedInstanceState: Bundle?) {
        binding?.apply {
            layoutManager = WrapContentLinearLayoutManager(act)
            adapter = SearchHistoryAdapter(R.layout.item_search_music_type_1, res) { holder, item ->
                holder.binding.avm = RecyclerViewSearchMusicResultViewModel(item,
                    addPlaylistItemCase, act.applicationContext).apply {
                    onAttach(this@SearchResultFragment)
                    clickEvent = { (activity as SearchActivity).openBottomSheet(it) }
                }
            }
            loadmore = object : RecyclerViewScrollCallback {
                override fun loadMoreEvent(recyclerView: RecyclerView, total: Int) {
                    if (resInfo.canLoadMoreFlag && !resInfo.isLoading) {
                        resInfo.isLoading = true
                        // OPTIMIZE(jieyi): 11/21/17 Modify the page constant.
                        viewModel.sendSearchRequest(keyword, res.size / 10, updateListInfo)
                    }
                }
            }
            pBkgImageUrl = bkgImageUrl
            fogViewColor = fgFogColor
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
    //endregion

    /**
     * An anonymous callback function for updating the recyclerview list and the item lists
     * from the viewholder of the loading more event.
     */
    private val updateListInfo = { keyword: String, musics: MutableList<MusicEntity.Music>, canLoadMore: Boolean ->
        res = (binding?.adapter as SearchHistoryAdapter).
            refresh(res, ArrayList(res).apply { addAll(musics) }).
            toMutableList()
        // TODO(jieyi): 9/28/17 Close the loading item or view.
        resInfo.isLoading = false
        // Raise the stopping loading more data flag for avoiding to load again.
        resInfo.canLoadMoreFlag = canLoadMore
    }
}