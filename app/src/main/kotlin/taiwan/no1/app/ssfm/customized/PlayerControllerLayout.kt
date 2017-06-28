package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import org.jetbrains.anko.imageButton
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   6/28/17
 */
class PlayerControllerLayout: ViewGroup {
    val listControllers = listOf(R.drawable.ic_repeat,
        R.drawable.ic_skip_previous,
        R.drawable.ic_play_circle,
        R.drawable.ic_skip_next,
        R.drawable.ic_shuffle)
    val listImageButtons = listOf(imageButton(), imageButton(), imageButton(), imageButton(), imageButton())

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val childrenCount = this.childCount
    }
}