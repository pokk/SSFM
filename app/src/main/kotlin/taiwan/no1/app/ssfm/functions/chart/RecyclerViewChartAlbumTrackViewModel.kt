package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(val item: BaseEntity): BaseViewModel() {
    val trackName by lazy { ObservableField<String>((item as TrackEntity.Track).name) }
    val trackNumber by lazy { ObservableField<String>((item as TrackEntity.Track).attr.rank ?: "0") }
    val trackDuration by lazy { ObservableField<String>((item as TrackEntity.Track).duration.toInt().toTimeString()) }

    fun trackOnClick(view: View) {
    }
}