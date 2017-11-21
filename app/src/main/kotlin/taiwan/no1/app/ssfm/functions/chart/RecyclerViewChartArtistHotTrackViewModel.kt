package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   10/29/17
 */
class RecyclerViewChartArtistHotTrackViewModel(val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val trackNumber by lazy { ObservableField<String>() }

    init {
        (item as TrackEntity.TrackWithStreamableString).let {
            trackName.set(it.name)
            trackNumber.set(it.attr?.rank ?: 0.toString())
        }
    }

    /**
     * A callback event for clicking a item to list item.
     *
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY,
            (item as TrackEntity.TrackWithStreamableString).name)
    }
}