package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.BaseObservable
import android.databinding.ObservableField
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logi
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.launch
import org.jsoup.Jsoup
import taiwan.no1.app.ssfm.mvvm.models.entities.ExtTrackEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewTrackChartViewModel(val item: ExtTrackEntity): BaseObservable() {
    val trackName by lazy { ObservableField<String>(item.track.name) }
    val artistName by lazy { ObservableField<String>(item.track.artist) }
    val thumbnail by lazy { ObservableField<String>("") }

    init {
        if (item.imageUrl.isBlank()) retrieveThumbnail(item.track.url) else thumbnail.set(item.imageUrl)
    }

    private fun retrieveThumbnail(url: String) {
        try {
            launch(CommonPool) {
                val document = Jsoup.connect(url).get()
                val classes = document.getElementsByClass("cover-art")
                if (classes.isNotEmpty()) {
                    item.imageUrl = classes[0].attr("src")
                    thumbnail.set(item.imageUrl)
                }
            }
        }
        catch (e: Exception) {
            loge(e)
            logi("Retrieve again!")
            retrieveThumbnail(url)
        }
    }
}