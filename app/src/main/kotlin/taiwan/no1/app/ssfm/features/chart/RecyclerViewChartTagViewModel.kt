package taiwan.no1.app.ssfm.features.chart

import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.RXBUS_PARAMETER_FRAGMENT
import taiwan.no1.app.ssfm.misc.constants.Constant.RXBUS_PARAMETER_FRAGMENT_NEEDBACK
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.NAVIGATION_TO_FRAGMENT
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import java.util.Random

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartTagViewModel(private var item: BaseEntity) : BaseViewModel() {
    val tagName by lazy { ObservableField<String>() }
    val background by lazy {
        fun randomColor(): Int =
            Random().let {
                Color.argb(it.nextInt(256),
                           it.nextInt(256),
                           it.nextInt(256),
                           it.nextInt(256))
            }

        val background = GradientDrawable(GradientDrawable.Orientation.BR_TL, intArrayOf(randomColor(), randomColor()))
        ObservableField<Drawable>(background)
    }

    init {
        refreshView()
    }

    fun setTagItem(item: BaseEntity) {
        this.item = item
        refreshView()
    }

    /**
     * @param view View
     *
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.navigate]
     */
    fun tagOnClick(view: View) {
        RxBus.get().post(NAVIGATION_TO_FRAGMENT,
                         hashMapOf(RXBUS_PARAMETER_FRAGMENT to ChartTagDetailFragment.newInstance((item as TagEntity.Tag).name.orEmpty()),
                                   RXBUS_PARAMETER_FRAGMENT_NEEDBACK to true))
    }

    private fun refreshView() {
        (item as TagEntity.Tag).let { tagName.set(it.name?.apply { this[0].toUpperCase() }) }
    }
}