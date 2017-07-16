package taiwan.no1.app.ssfm.customized

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.mikhaellopez.circularimageview.CircularImageView
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.R

/**
 * [CircleImageView] with rotated animation.
 *
 * @author  jieyi
 * @since   7/4/17
 */
open class RotatedCircleImageView: CircularImageView {
    companion object {
        private const val ROTATE_TIME = 10
    }

    var onClickEvent: ((RotatedCircleImageView) -> Unit)? = null
    var rotateTime = ROTATE_TIME * 1000L
    val rotateAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)!!.also {
        it.interpolator = LinearInterpolator()
        it.duration = this.rotateTime
        it.repeatCount = Animation.INFINITE
    }
    var isPauseState = false

    constructor(context: Context): super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet): super(context, attrs) {
        init(context, attrs, 0)

    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr) {
        init(context, attrs, defStyleAttr)
    }

    fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        context.obtainStyledAttributes(attrs, R.styleable.RotatedCircleImageView, defStyleAttr, 0).also {
            this.rotateTime = it.getInteger(R.styleable.RotatedCircleImageView_rotate_sec, ROTATE_TIME) * 1000L
        }.recycle()

        this.onClick {
            this@RotatedCircleImageView.rotateAnimator.let {
                when {
                    !it.isStarted -> {
                        it.start()
                        this@RotatedCircleImageView.isPauseState = false
                    }
                    it.isPaused -> {
                        it.resume()
                        this@RotatedCircleImageView.isPauseState = false
                    }
                    it.isRunning -> {
                        it.pause()
                        this@RotatedCircleImageView.isPauseState = true
                    }
                }
            }
            this@RotatedCircleImageView.onClickEvent?.let { it(this@RotatedCircleImageView) }
        }
    }
}