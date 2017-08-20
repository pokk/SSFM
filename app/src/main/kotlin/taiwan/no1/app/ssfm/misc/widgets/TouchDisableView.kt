package taiwan.no1.app.ssfm.misc.widgets


import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

internal class TouchDisableView
@JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0):
    ViewGroup(context, attrs, defStyleAttr) {
    var content: View? = null
        set(v) {
            this.removeView(field)
            field = v
            this.addView(content)
        }

    //	private int mMode;
    var touchDisabled = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.getDefaultSize(0, widthMeasureSpec)
        val height = View.getDefaultSize(0, heightMeasureSpec)

        this.setMeasuredDimension(width, height)

        val contentWidth = ViewGroup.getChildMeasureSpec(widthMeasureSpec, 0, width)
        val contentHeight = ViewGroup.getChildMeasureSpec(heightMeasureSpec, 0, height)

        this.content!!.measure(contentWidth, contentHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val width = r - l
        val height = b - t

        this.content!!.layout(0, 0, width, height)
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean = this.touchDisabled
}