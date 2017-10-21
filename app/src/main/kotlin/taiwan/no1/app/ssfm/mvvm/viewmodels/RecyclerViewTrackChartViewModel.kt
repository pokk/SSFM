package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewTrackChartViewModel(val item: TrackEntity.Track): BaseViewModel() {
    val trackName by lazy { ObservableField<String>(item.name) }
    val artistName by lazy { ObservableField<String>(item.artist?.name ?: "") }
    val thumbnail by lazy { ObservableField<String>(item.images?.get(LARGE)?.text) }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun trackOnClick(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, item.name)
    }
}