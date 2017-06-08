package taiwan.no1.app.ssfm.mvvm.ui.customize


import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

internal class TouchDisableView @JvmOverloads constructor(context: Context,
                                                          attrs: AttributeSet? = null): ViewGroup(
    context,
    attrs) {

    var content: View? = null
        set(v) {
            if (content != null) {
                this.removeView(content)
            }
            field = v
            addView(content)
        }

    //	private int mMode;
    var isTouchDisabled = false
        private set

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val width = View.getDefaultSize(0, widthMeasureSpec)
        val height = View.getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)

        val contentWidth = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, width)
        val contentHeight = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, height)
        content!!.measure(contentWidth, contentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        val height = b - t
        content!!.layout(0, 0, width, height)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        return isTouchDisabled
    }

    fun setTouchDisable(disableTouch: Boolean) {
        isTouchDisabled = disableTouch
    }
}