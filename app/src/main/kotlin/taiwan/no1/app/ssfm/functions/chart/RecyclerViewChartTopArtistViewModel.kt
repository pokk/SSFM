package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartTopArtistViewModel(val item: ArtistEntity.Artist): BaseViewModel() {
    val artistName by lazy { ObservableField<String>(item.name) }
    val playCount by lazy { ObservableField<String>(item.playCount) }
    val thumbnail by lazy { ObservableField<String>(item.images?.get(ImageSizes.EXTRA_LARGE)?.text ?: "") }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun artistOnClick(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, item.name)
    }
}