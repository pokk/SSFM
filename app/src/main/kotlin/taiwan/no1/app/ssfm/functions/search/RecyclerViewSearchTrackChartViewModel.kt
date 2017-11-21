package taiwan.no1.app.ssfm.functions.search

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchTrackChartViewModel(val item: BaseEntity) : BaseViewModel() {
    val trackName by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }

    init {
        (item as TrackEntity.Track).let {
            trackName.set(it.name)
            artistName.set(it.artist?.name ?: "")
            thumbnail.set(it.images?.get(LARGE)?.text ?: "")
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
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_HISTORY, (item as TrackEntity.Track).name)
    }
}