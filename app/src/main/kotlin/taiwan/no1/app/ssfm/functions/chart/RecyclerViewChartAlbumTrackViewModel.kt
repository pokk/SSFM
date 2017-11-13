package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.toTimeString
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }
    val trackDuration by lazy { ObservableField<String>() }

    init {
        (item as TrackEntity.Track).let {
            trackName.set(it.name)
            trackNumber.set(it.attr?.rank ?: 0.toString())
            trackDuration.set(it.duration?.toInt()?.toTimeString())
        }
    }

    fun trackOnClick(view: View) {
    }
}