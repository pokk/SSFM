package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.BaseObservable
import android.databinding.ObservableField
import de.umass.lastfm.Track
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.jsoup.Jsoup

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewTrackChartViewModel(val item: Track): BaseObservable() {
    val trackName by lazy { ObservableField<String>(item.name) }
    val artistName by lazy { ObservableField<String>(item.artist) }
    val thumbnail by lazy { ObservableField<String>("") }

    init {
        retrieveThumbnail(item.url)
    }

    private fun retrieveThumbnail(url: String) {
        launch(CommonPool) {
            val document = Jsoup.connect(url).get()
            val classes = document.getElementsByClass("avatar")
            if (classes.isNotEmpty())
                thumbnail.set(classes[0].attr("src"))
        }
    }
}