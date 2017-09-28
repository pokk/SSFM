package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import com.hwangjr.rxbus.RxBus
import com.hwangjr.rxbus.annotation.Subscribe
import com.hwangjr.rxbus.annotation.Tag
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.toObservable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_search_result.rv_music_result
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ItemSearchMusicType1Binding
import taiwan.no1.app.ssfm.misc.constants.Constant
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.viewmodels.RecyclerViewMusicResultViewModel
import taiwan.no1.app.ssfm.mvvm.views.BaseFragment
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.adapters.BaseDataBindingAdapter
import kotlin.properties.Delegates

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class SearchResultFragment: BaseFragment() {
    private var adapter by Delegates.notNull<BaseDataBindingAdapter<ItemSearchMusicType1Binding, InfoBean>>()
    private var res = mutableListOf<InfoBean>()
    private var isLoading = false
    private var canLoadMoreFlag = true
    /** @to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.loadMoreResult] */
    private val recyclerViewScrollListener = BaseDataBindingAdapter.OnScrollListener { total ->
        if (canLoadMoreFlag && !isLoading) {
            RxBus.get().post(RxBusConstant.QUERY_LOAD_MORE, total)
            // TODO(jieyi): 9/28/17 Show the loading item or view.
            isLoading = true
        }
    }

    //region Fragment lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxBus.get().register(this)
        // Due to this object is kept by `SearchActivity`, this list need to be cleared every time.
        res.clear()
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }
    //endregion

    override fun init(savedInstanceState: Bundle?) {
        adapter = BaseDataBindingAdapter(R.layout.item_search_music_type_1, res) { block, item ->
            block.binding.avm = RecyclerViewMusicResultViewModel(item, activity)
        }
        rv_music_result.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = this@SearchResultFragment.adapter
            addOnScrollListener(recyclerViewScrollListener)
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result

    /**
     * Receiving the new music data from the remote database.
     *
     * @param hashMap [RxBusConstant.HASH_MORE_DATA_INIT] is [Boolean] type for checking list whether the list
     *                needs to clear or not.
     *                [RxBusConstant.HASH_MORE_DATA_ENTITY] is [SearchMusicEntity] type for the music list which
     *                retrieved from the remote database.
     *
     * @from [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.queryMoreResult]
     */
    @Subscribe(tags = arrayOf(Tag(RxBusConstant.FRAGMENT_SEARCH_RESULT)))
    fun receiveMusicRes(hashMap: java.util.AbstractMap<String, Any>) {
        val entity = hashMap[RxBusConstant.HASH_MORE_DATA_ENTITY] as SearchMusicEntity
        if (hashMap[RxBusConstant.HASH_MORE_DATA_INIT] as Boolean) {
            res.clear()
        }

        // NOTE(jieyi): 9/27/17 This function should be in the view model normally. I just reduced it.
        entity.data?.info?.toObservable()?.
            filter { (it.singername?.isNotEmpty() == true) && (it.songname?.isNotEmpty() == true) }?.
            subscribeOn(Schedulers.io())?.
            toList()?.
            observeOn(AndroidSchedulers.mainThread())?.
            subscribe { list, _ ->
                res = adapter.refresh(res, ArrayList(res).apply { addAll(list) }).toMutableList()
                // TODO(jieyi): 9/28/17 Close the loading item or view.
                isLoading = false
            }
        // Raise the stop loading more data flag.
        canLoadMoreFlag = Constant.QUERY_PAGE_SIZE <= (entity.data?.info?.size ?: 0)
    }
}