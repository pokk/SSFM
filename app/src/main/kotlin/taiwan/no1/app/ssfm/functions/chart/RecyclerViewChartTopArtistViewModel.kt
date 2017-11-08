package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartTopArtistViewModel(val item: BaseEntity): BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val playCount by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }

    init {
        (item as ArtistEntity.Artist).let {
            artistName.set(it.name)
            playCount.set(it.playCount)
            thumbnail.set(it.images?.get(EXTRA_LARGE)?.text ?: "")
        }
    }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.search.SearchViewModel.receiveClickHistoryEvent]
     */
    fun artistOnClick(view: View) {
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, (item as ArtistEntity.Artist).name)
    }
}