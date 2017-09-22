package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.devrapid.kotlinknifer.logd
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        RxBus.get().register(this)
    }

    override fun init(savedInstanceState: Bundle?) {
        adapter = BaseDataBindingAdapter(R.layout.item_search_music_type_1, res) { block, item ->
            block.binding.avm = MusicResultViewModel(item, activity)
        }
        rv_music_result.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@SearchResultFragment.adapter
        }
    }

    override fun onDestroy() {
        RxBus.get().unregister(this)
        super.onDestroy()
    }

    override fun provideInflateView(): Int = R.layout.fragment_search_result

    @Subscribe(tags = arrayOf(Tag(RxBusConstant.SEARCH_RESULT)))
    fun test(entity: SearchMusicEntity) {
        res.clear()
        // FIXME(jieyi): 9/23/17 If input 'five bob', the app must crash.
        logd(entity.data?.info)
        entity.data?.info?.toObservable()?.
            filter { (it.singername?.isNotEmpty() == true) && (it.songname?.isNotEmpty() == true) }?.
            subscribeOn(Schedulers.io())?.
            observeOn(AndroidSchedulers.mainThread())?.toList()?.
            subscribe { list, throwable ->
                adapter.refresh(res, res.apply { addAll(list) })
            }
    }
}