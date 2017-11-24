package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity

/**
 *
 * @author  jieyi
 * @since   11/1/17
 */
class RecyclerViewChartRankChartViewModel(val item: BaseEntity) : BaseViewModel() {
    val coverUrl by lazy { ObservableField<String>() }
    val name by lazy { ObservableField<String>() }
    val update by lazy { ObservableField<String>() }

    init {
        (item as RankChartEntity).let {
            coverUrl.set(it.coverUrl)
            name.set(it.chartName)
            update.set(it.updateTime)
        }
    }

    /**
     * A callback event for clicking an artist to list item.
     *
     * @hashCode view [android.widget.RelativeLayout]
     *
     * @event_to [taiwan.no1.app.ssfm.functions.chart.ChartActivity.navigateToRankChartDetail]
     */
    fun chartOnClick(view: View) {
        RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_RANK_CHART, (item as RankChartEntity).rankType.toString())
    }
}