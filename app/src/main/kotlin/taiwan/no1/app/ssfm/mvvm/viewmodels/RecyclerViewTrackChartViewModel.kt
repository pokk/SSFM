package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.BaseObservable
import android.databinding.ObservableField
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
        retrieveThumbnail(item.track.url)
    }

    private fun retrieveThumbnail(url: String) {
    }
}