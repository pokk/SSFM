package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
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

    fun chartOnClick(view: View) {
    }
}