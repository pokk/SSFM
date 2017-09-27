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
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.utilies.WrapContentLinearLayoutManager
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity.InfoBean
import taiwan.no1.app.ssfm.mvvm.viewmodels.MusicResultViewModel
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
            block.binding.avm = MusicResultViewModel(item, activity)
        }
        rv_music_result.apply {
            layoutManager = WrapContentLinearLayoutManager(activity)
            adapter = this@SearchResultFragment.adapter
            addOnScrollListener(BaseDataBindingAdapter.OnScrollListener {
                RxBus.get().post(RxBusConstant.QUERY_LOAD_MORE, it)
            })
        }
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result

    @Subscribe(tags = arrayOf(Tag(RxBusConstant.FRAGMENT_SEARCH_RESULT)))
    fun recevieMusicRes(entity: SearchMusicEntity) {
        // NOTE(jieyi): 9/27/17 This function should be in the view model normally. I just reduced it.
        entity.data?.info?.toObservable()?.
            filter { (it.singername?.isNotEmpty() == true) && (it.songname?.isNotEmpty() == true) }?.
            subscribeOn(Schedulers.io())?.
            toList()?.
            doOnSuccess { res.clear() }?.
            observeOn(AndroidSchedulers.mainThread())?.
            subscribe { list, _ ->
                adapter.refresh(res, res.apply { addAll(list) })
            }
    }
}