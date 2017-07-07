package taiwan.no1.app.ssfm.customized

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
    private val MIN_ALPHA = 0.5f

    override fun transformPage(view: View, position: Float) {
        val viewWidth = view.width
        val viewHeight = view.height

        val mViewPager = view.parent as ViewPager
        val mMaxTranslateOffsetX = 800

        logw(position, view)

        when {
//         [-Infinity,-1)
            position < -1 -> {
                view.alpha = 0f
                view.translationX -= 100
            }
        // [-1,0]
            position in (0..1) -> {
                val leftInScreen = view.left - mViewPager.scrollX
                val centerXInViewviewr = leftInScreen + view.measuredWidth / 2
                val offsetX = centerXInViewviewr - mViewPager.measuredWidth / 2
                val offsetRate = offsetX.toFloat() * 0.38f / mViewPager.measuredWidth
                val scaleFactor = 1 - Math.abs(offsetRate)
                if (scaleFactor > 0) {
                    view.scaleX = scaleFactor
                    view.scaleY = scaleFactor
                    view.translationX = -mMaxTranslateOffsetX * offsetRate
                }
            }
        // (0,1]
//            position <= 1 -> {
//                // Fade the view out.
//                view.alpha = 1 - position
//
//                // Counteract the default slide transition
//                view.translationX = viewWidth * -position
//
//                // Scale the view down (between MIN_SCALE and 1)
//                val scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(position))
//                view.scaleX = scaleFactor
//                view.scaleY = scaleFactor
//            }
        // (1,+Infinity]
            else -> {
                view.alpha = 0f
                view.translationX += 100
            }
        }
    }
}