package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.BaseObservable
import android.databinding.ObservableField
import com.devrapid.kotlinknifer.observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.Jsoup
import taiwan.no1.app.ssfm.mvvm.models.entities.ExtArtistEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewArtistChartViewModel(val item: ExtArtistEntity): BaseObservable() {
    val artistName by lazy { ObservableField<String>(item.artist.name) }
    val thumbnail by lazy { ObservableField<String>("") }

    init {
        if (item.imageUrl.isBlank()) retrieveThumbnail(item.artist.url) else thumbnail.set(item.imageUrl)
    }

    private fun retrieveThumbnail(url: String) {
        // TODO(jieyi): 10/14/17 Set init image for the all artists' image in the first loading time.
        // OPTIMIZE(jieyi): 10/14/17 RxJava
        observable<String> {
            val document = Jsoup.connect(url).get()
            val classes = document.getElementsByClass("avatar")
            it.onNext(if (classes.isNotEmpty()) classes[0].attr("src") else "")
        }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe {
            it.takeIf { it.isNotBlank() }?.let {
                item.imageUrl = it
                thumbnail.set(it)
            }
        }
        // OPTIMIZE(jieyi): 10/14/17 Kotlin Coroutines
//        launch(CommonPool) {
//            val document = Jsoup.connect(url).get()
//            val classes = document.getElementsByClass("avatar")
//            if (classes.isNotEmpty() && classes[0].attr("src") != thumbnail.get()) {
//                item.imageUrl = classes[0].attr("src")
//                thumbnail.set(item.imageUrl)
//            }
//        }
    }
}