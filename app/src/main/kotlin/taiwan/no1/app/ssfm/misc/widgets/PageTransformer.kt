package taiwan.no1.app.ssfm.misc.widgets

import android.support.v4.view.ViewPager
import android.view.View
import com.devrapid.kotlinknifer.logw

/**
 *
 * @author  jieyi
 * @since   7/6/17
 */
class PageTransformer: ViewPager.PageTransformer {
    private val MIN_SCALE = 0.75f

    override fun transformPage(view: View, position: Float) {
        val pageWidth = view.width

        logw(position, view)

        when {
        // [-Infinity,-1)
            position < -1 -> {
                view.alpha = 0f
            }
        // [-1,0]
            position <= 0 -> {
                view.alpha = 1f
                view.translationX = 0f
                view.scaleX = 1f
                view.scaleY = 1f
            }
        // (0,1]
            position <= 1 -> {
                // Fade the page out.
                view.alpha = 1 - position

                // Counteract the default slide transition
                view.translationX = pageWidth * -position

                // Scale the page down (between MIN_SCALE and 1)
                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
                view.scaleX = scaleFactor
                view.scaleY = scaleFactor
            }
        // (1,+Infinity]
            else -> {
                view.alpha = 0f
            }
        }
    }
}