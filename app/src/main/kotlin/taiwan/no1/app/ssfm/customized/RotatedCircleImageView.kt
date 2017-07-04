package taiwan.no1.app.ssfm.customized

import android.animation.ObjectAnimator
import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   7/4/17
 */
class RotatedCircleImageView: CircleImageView {
    companion object {
        private const val ROTATE_TIME = 10
    }

    // TODO(jieyi): 7/5/17 This variable cannot be set temporally.
    var rotateTime = ROTATE_TIME * 1000L
    var onClick = { _: ImageView -> }
    val rotateAnimator = ObjectAnimator.ofFloat(this, "rotation", 0f, 360f)!!.also {
        it.interpolator = LinearInterpolator()
        it.duration = this.rotateTime
        it.repeatCount = Animation.INFINITE
    }

    constructor(context: Context): super(context)
    constructor(context: Context, attrs: AttributeSet): super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int): super(context, attrs, defStyleAttr)

    override fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        super.init(context, attrs, defStyleAttr)

        context.obtainStyledAttributes(attrs, R.styleable.RotatedCircleImageView, defStyleAttr, 0).also {
            this.rotateTime = it.getInteger(R.styleable.RotatedCircleImageView_rotate_sec,
                ROTATE_TIME) * 1000L
        }.recycle()

        this.onClick {
            this@RotatedCircleImageView.rotateAnimator.let {
                when {
                    !it.isStarted -> it.start()
                    it.isPaused -> it.resume()
                    it.isRunning -> it.pause()
                }
            }
            this@RotatedCircleImageView.onClick(this@RotatedCircleImageView)
        }
    }
}