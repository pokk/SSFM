package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_search_result.rv_music_result
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.FragmentSearchResultBinding
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.viewmodels.FragmentSearchResultViewModel
import taiwan.no1.app.ssfm.mvvm.viewmodels.MusicResultViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment: AdvancedFragment<FragmentSearchResultViewModel, FragmentSearchResultBinding>() {
    override var viewModel: FragmentSearchResultViewModel = FragmentSearchResultViewModel()
    private var adapter by Delegates.notNull<BaseDataBindingAdapter<ItemSearchMusicType1Binding, InfoBean>>()

    override fun init(savedInstanceState: Bundle?) {
        val res = mutableListOf(InfoBean(singername = "Lady Gaga", songname = "The cure", duration = 200),
            InfoBean(singername = "Ariana", songname = "Last Christmas", duration = 231),
            InfoBean(singername = "Talyer Swift", songname = "What did you make me do", duration = 421),
            InfoBean(singername = "Jieyi Wu", songname = "Taiwan NO1", duration = 321),
            InfoBean(singername = "Bruno Mars", songname = "What do I like to do", duration = 113),
            InfoBean(singername = "Nikical Minaji", songname = "Bass", duration = 352),
            InfoBean(singername = "XXXXX", songname = "?????", duration = 211))

        adapter = BaseDataBindingAdapter<ItemSearchMusicType1Binding, InfoBean>(R.layout.item_search_music_type_1) { block, position ->
            block.binding.avm = MusicResultViewModel(res[position], activity)
        }.apply { dataSize = res.size }

        rv_music_result.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@SearchResultFragment.adapter
        }

//        timer(initialDelay = 2, period = 1) {
//            adapter.refresh(res, res.apply {
//                add(InfoBean(singername = "1321", songname = "fdafds", duration = 3213))
//                add(InfoBean(singername = "321", songname = "fdafds", duration = 33))
//                add(InfoBean(singername = "fdafds", songname = "fdasfasd", duration = 321))
//                add(InfoBean(singername = "fdafd", songname = "fdsaf", duration = 111))
//                add(InfoBean(singername = "fdafdsa", songname = "fdafads", duration = 33))
//            })
//        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
}