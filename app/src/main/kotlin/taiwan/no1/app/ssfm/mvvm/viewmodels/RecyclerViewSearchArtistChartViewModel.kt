package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.constants.RxBusConstant
import taiwan.no1.app.ssfm.misc.extension.formatToMoneyKarma
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewSearchArtistChartViewModel(val item: BaseEntity): BaseViewModel() {
    val artistName by lazy { ObservableField<String>((item as ArtistEntity.Artist).name) }
    val playCount by lazy {
        val count = ((item as ArtistEntity.Artist).playCount?.toInt() ?: 0) / 1000

        ObservableField<String>("${count.toString().formatToMoneyKarma()}K")
    }
    val thumbnail by lazy { ObservableField<String>((item as ArtistEntity.Artist).images?.get(EXTRA_LARGE)?.text ?: "") }
    var clickItemListener: ((item: ArtistEntity.Artist) -> Unit)? = null

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun artistOnClick(view: View) {
        // For `searching activity`.
        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, (item as ArtistEntity.Artist).name)
        // For `top chart activity`.
        clickItemListener?.invoke(item)
    }
}