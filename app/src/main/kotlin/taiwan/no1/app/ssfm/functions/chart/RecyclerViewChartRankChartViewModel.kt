package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity

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
        (item as MusicRankEntity).let {
            name.set(it.data.timestamp.toString())
        }
    }

    fun chartOnClick(view: View) {
    }
}