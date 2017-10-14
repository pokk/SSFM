package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.observable
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import taiwan.no1.app.ssfm.mvvm.models.entities.ExtArtistEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewArtistChartViewModel(val item: ExtArtistEntity): BaseViewModel() {
    val artistName by lazy { ObservableField<String>(item.artist.name) }
    val thumbnail by lazy { ObservableField<String>("") }

    fun artistOnClick(view: View) {
    }

    fun retrieveThumbnail() {
        // TODO(jieyi): 10/14/17 Set init image for the all artists' image in the first loading time.
        item.imageUrl.takeIf { it.isNotBlank() }?.let { thumbnail.set(it) } ?:
            observable<String> {
                val document = Jsoup.connect(item.artist.url).get()
                val classes = document.getElementsByClass("avatar")
                it.onNext(if (classes.isNotEmpty()) classes[0].attr("src") else "")
            }.subscribeOn(Schedulers.io()).
                bindToLifecycle(lifecycleProvider).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe {
                    it.takeIf { it.isNotBlank() }?.let {
                        item.imageUrl = it
                        thumbnail.set(it)
                    }
                }
    }
}