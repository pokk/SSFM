package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartAlbumTrackViewModel(val item: BaseEntity): BaseViewModel() {
    val trackName by lazy { ObservableField<String>((item as TrackEntity.Track).name) }
    val trackNumber by lazy { ObservableField<String>((item as TrackEntity.Track).attr?.rank ?: "0") }

    fun trackOnClick(view: View) {
    }
}