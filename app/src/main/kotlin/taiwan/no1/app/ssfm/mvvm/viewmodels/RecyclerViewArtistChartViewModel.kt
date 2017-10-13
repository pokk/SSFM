package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.content.Context
import android.databinding.BaseObservable
import android.databinding.ObservableField
import de.umass.lastfm.Artist
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.jsoup.Jsoup

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewArtistChartViewModel(val item: Artist, context: Context): BaseObservable() {
    val artistName by lazy { ObservableField<String>(item.name) }
    val thumbnail by lazy { ObservableField<String>("") }

    init {
        retrieveThumbnail(item.url)
    }

    private fun retrieveThumbnail(url: String) {
        // FIXME(jieyi): 10/13/17 When item appear again, it will retrieve again and again.
        launch(CommonPool) {
            val document = Jsoup.connect(url).get()
            val classes = document.getElementsByClass("avatar")
            if (classes.isNotEmpty() && classes[0].attr("src") != thumbnail.get())
                thumbnail.set(classes[0].attr("src"))
        }
    }
}