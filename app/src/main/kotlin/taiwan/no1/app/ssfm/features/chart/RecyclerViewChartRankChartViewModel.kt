package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity

/**
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartRankChartViewModel(private var item: BaseEntity) : BaseViewModel() {
    val coverUrl by lazy { ObservableField<String>() }
    val name by lazy { ObservableField<String>() }
    val update by lazy { ObservableField<String>() }

    init {
        refreshView()
    }

    fun setChartItem(item: BaseEntity) {
        this.item = item
        refreshView()
    }

    /**
     * A callback event for clicking an artist to list item.
     *
     * @param view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.navigateToRankChartDetail]
     */
    fun chartOnClick(view: View) {
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_RANK_CHART, item)
    }

    private fun refreshView() {
        (item as RankChartEntity).let {
            coverUrl.set(it.coverUrl)
            name.set(it.chartName)
            update.set(it.updateTime)
        }
    }
}