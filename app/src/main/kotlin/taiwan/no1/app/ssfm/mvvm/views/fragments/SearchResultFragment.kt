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
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.MusicResultAdapter

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment: AdvancedFragment<FragmentSearchResultViewModel, FragmentSearchResultBinding>() {
    override var viewModel: FragmentSearchResultViewModel = FragmentSearchResultViewModel()

    override fun init(savedInstanceState: Bundle?) {
        val res = mutableListOf(InfoBean(singername = "Lady Gaga", songname = "The cure", duration = 200),
            InfoBean(singername = "Ariana", songname = "Last Christmas", duration = 231),
            InfoBean(singername = "Talyer Swift", songname = "What did you make me do", duration = 421),
            InfoBean(singername = "Jieyi Wu", songname = "Taiwan NO1", duration = 321),
            InfoBean(singername = "Bruno Mars", songname = "What do I like to do", duration = 113),
            InfoBean(singername = "Nikical Minaji", songname = "Bass", duration = 352),
            InfoBean(singername = "XXXXX", songname = "?????", duration = 211))

        val layoutManager = LinearLayoutManager(this.activity)
//        val adapter = MusicResultAdapter(res, this.activity)
        rv_music_result.layoutManager = layoutManager
        rv_music_result.adapter = MusicResultAdapter<ItemSearchMusicType1Binding>(R.layout.item_search_music_type_1) { holder, position ->
            holder.binding.avm = MusicResultViewModel(res[position], this.activity)
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result
}