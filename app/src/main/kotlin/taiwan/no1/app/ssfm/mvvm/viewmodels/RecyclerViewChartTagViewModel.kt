package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartTagViewModel(val item: TagEntity.Tag): BaseViewModel() {
    val tagName by lazy { ObservableField<String>(item.name?.apply { this[0].toUpperCase() }) }

    /**
     * A callback event for clicking a item to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel.receiveClickHistoryEvent]
     */
    fun artistOnClick(view: View) {
//        RxBus.get().post(RxBusConstant.VIEWMODEL_CLICK_HISTORY, item.name)
    }
}