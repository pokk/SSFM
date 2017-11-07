package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   10/29/17
 */
class RecyclerViewChartArtistHotTrackViewModel(val item: BaseEntity): BaseViewModel() {
    val trackName by lazy { ObservableField<String>((item as TrackEntity.TrackWithStreamableString).name) }
    val trackNumber by lazy { ObservableField<String>((item as TrackEntity.TrackWithStreamableString).attr?.rank ?: "0") }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, (item as TrackEntity.TrackWithStreamableString).name)
    }
}