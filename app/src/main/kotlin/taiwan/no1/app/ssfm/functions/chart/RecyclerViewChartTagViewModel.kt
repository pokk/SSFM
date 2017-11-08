package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import java.util.Random

/**
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewChartTagViewModel(val item: BaseEntity): BaseViewModel() {
    val tagName by lazy { ObservableField<String>() }
    val background by lazy {
        fun randomColor(): Int =
            Random().let { Color.argb(it.nextInt(256), it.nextInt(256), it.nextInt(256), it.nextInt(256)) }

        val background = GradientDrawable(GradientDrawable.Orientation.BR_TL, intArrayOf(randomColor(), randomColor()))
        ObservableField<Drawable>(background)
    }
    var clickItemListener: ((item: TagEntity.Tag) -> Unit)? = null

    init {
        (item as TagEntity.Tag).let { tagName.set(it.name?.apply { this[0].toUpperCase() }) }
    }

    fun tagOnClick(view: View) {
        clickItemListener?.invoke(item as TagEntity.Tag)
    }
}