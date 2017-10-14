package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.observable
import com.devrapid.kotlinknifer.observer
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import taiwan.no1.app.ssfm.mvvm.models.entities.ExtTrackEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewTrackChartViewModel(val item: ExtTrackEntity): BaseViewModel() {
    val trackName by lazy { ObservableField<String>(item.track.name) }
    val artistName by lazy { ObservableField<String>(item.track.artist) }
    val thumbnail by lazy { ObservableField<String>("") }

    fun trackOnClick(view: View) {
    }

    fun retrieveThumbnail() {
        item.imageUrl.takeIf { it.isNotBlank() }?.let { thumbnail.set(it) } ?:
            observable<String> {
                val document = Jsoup.connect(item.track.url).get()
                val classes = document.getElementsByClass("cover-art")
                it.onNext(if (classes.isNotEmpty()) classes[0].attr("src") else "")
            }.subscribeOn(Schedulers.io()).
                bindToLifecycle(lifecycleProvider).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer(onNext = {
                    it.takeIf { it.isNotBlank() }?.let {
                        item.imageUrl = it
                        thumbnail.set(it)
                    }
                }, onError = {
                    loge(it.message)
                    loge(it)
                    retrieveThumbnail()
                }))
    }
}