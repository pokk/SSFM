package taiwan.no1.app.ssfm.mvvm.ui.customize

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.custom_menu_view.view.*
import kotlinx.android.synthetic.main.custom_scroll_menu.view.*
import taiwan.no1.app.ssfm.R


/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class SideMenu: FrameLayout {
    companion object {
        private const val PRESSED_MOVE_HORIZONTAL = 2
        private const val PRESSED_MOVE_VERTICAL = 3
        private const val PRESSED_DOWN = 4
        private const val PRESSED_DONE = 5
        private const val ROTATE_Y_ANGLE = 10f
        private const val ANIMATION_DURATION = 250L
    }

    lateinit var vScrollMenu: View
    var isOpened = false
        private set
    var menuListener: OnMenuListener? = null
    var menuItems = mutableListOf<MenuItem>()
        get() = field
        set(value) {
            field = value
            this.rebuildMenu()
        }
    var mScaleValue = 0.75f
    var mUse3D = false

    lateinit private var activity: Activity
    lateinit private var viewActivity: TouchDisableView
    lateinit private var viewDecor: ViewGroup
    private val llMenu by lazy { this.vScrollMenu.ll_menu }
    private val displayMetrics by lazy { DisplayMetrics() }
    private val screenHeight by lazy {
        this.activity.windowManager.defaultDisplay.getMetrics(this.displayMetrics)
        this.displayMetrics.heightPixels
    }
    private val screenWidth by lazy {
        this.activity.windowManager.defaultDisplay.getMetrics(this.displayMetrics)
        this.displayMetrics.widthPixels
    }
    private var ignoredViews = mutableListOf<View>()
    private var shadowAdjustScaleX = 0f
    private var shadowAdjustScaleY = 0f
    private var lastRawX = 0f
    private var lastActionDownX = 0f
    private var lastActionDownY = 0f
    private var pressedState = PRESSED_DOWN
    private var isInIgnoredView = false

    interface OnMenuListener {
        fun openMenu()
        fun closeMenu()
    }

    constructor(context: Context, @LayoutRes resMenu: Int = -1): super(context) {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).let {
            it.inflate(R.layout.custom_menu_view, this)

            this.vScrollMenu = it.inflate(if (0 <= resMenu) resMenu else R.layout.custom_scroll_menu, this, false)
            this.rl_menu_holder.addView(this.vScrollMenu)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentActivityScaleX = this.viewActivity.scaleX

        if (1.0f == currentActivityScaleX) {
            this.setScaleDirection()
        }

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastActionDownX = ev.x
                lastActionDownY = ev.y
                this.isInIgnoredView = this.isInIgnoredView(ev) && !this.isOpened
                this.pressedState = PRESSED_DOWN
            }
            MotionEvent.ACTION_MOVE -> run action_move@ {
                if ((PRESSED_DOWN != this.pressedState && PRESSED_MOVE_HORIZONTAL != this.pressedState) ||
                    this.isInIgnoredView) {
                    return@action_move
                }

                val (xOffset, yOffset) = Pair((ev.x - lastActionDownX).toInt(), (ev.y - lastActionDownY).toInt())

                if (PRESSED_DOWN == this.pressedState) {
                    if (25 < yOffset || -25 > yOffset) {
                        this.pressedState = PRESSED_MOVE_VERTICAL

                        return@action_move
                    }
                    if (50 < xOffset || -50 > xOffset) {
                        this.pressedState = PRESSED_MOVE_HORIZONTAL
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                }
                else if (PRESSED_MOVE_HORIZONTAL == this.pressedState) {
                    if (0.95 > currentActivityScaleX) {
                        showScrollViewMenu(this.vScrollMenu)
                    }

                    val targetScale = getTargetScale(ev.rawX)
                    if (this.mUse3D) {
                        val angle = (-1 * ROTATE_Y_ANGLE) * ((1 - targetScale) * 2).toInt()

                        this.viewActivity.rotationY = angle
                        this.iv_shadow.scaleX = targetScale - shadowAdjustScaleX
                        this.iv_shadow.scaleY = targetScale - shadowAdjustScaleY
                    }
                    else {
                        this.iv_shadow.scaleX = targetScale + shadowAdjustScaleX
                        this.iv_shadow.scaleY = targetScale + shadowAdjustScaleY
                    }
                    this.viewActivity.scaleX = targetScale
                    this.viewActivity.scaleY = targetScale
                    this.llMenu.alpha = (1 - targetScale) * 2.0f

                    lastRawX = ev.rawX

                    return true
                }
            }
            MotionEvent.ACTION_UP -> run action_up@ {
                if (this.isInIgnoredView || PRESSED_MOVE_HORIZONTAL != this.pressedState) {
                    return@action_up
                }

                this.pressedState = PRESSED_DONE
                if (this.isOpened) {
                    if (0.56f < currentActivityScaleX) this.closeMenu() else this.openMenu()
                }
                else {
                    if (0.94f > currentActivityScaleX) this.openMenu() else this.closeMenu()
                }
            }
        }
        lastRawX = ev.rawX

        return super.dispatchTouchEvent(ev)
    }

    fun attachActivity(activity: Activity) {
        this.initValue(activity)
        this.setShadowAdjustScaleXByOrientation()
        this.viewDecor.addView(this, 0)
    }

    fun setBackground(@DrawableRes resBackground: Int) = this.iv_background.setImageResource(resBackground)

    fun setShadowVisible(isVisible: Boolean) = this.iv_shadow.setBackgroundResource(if (isVisible) R.drawable.shadow else 0)

    fun addMenuItem(menuItem: MenuItem) {
        this.menuItems.add(menuItem)
        this.llMenu.addView(menuItem)
    }

    fun addIgnoredView(v: View) = this.ignoredViews.add(v)

    fun removeIgnoredView(v: View) = this.ignoredViews.remove(v)

    fun clearIgnoredViewList() = this.ignoredViews.clear()

    fun openMenu() {
        this.setScaleDirection()

        this.isOpened = true
        val scaleDown_activity = buildScaleDownAnimation(this.viewActivity, this.mScaleValue, this.mScaleValue)
        val scaleDown_shadow = buildScaleDownAnimation(this.iv_shadow, this.mScaleValue + this.shadowAdjustScaleX,
            this.mScaleValue + this.shadowAdjustScaleY)
        val alpha_menu = buildMenuAnimation(this.llMenu, 1.0f)

        scaleDown_shadow.addListener(this.animationListener)
        scaleDown_activity.also { it.playTogether(scaleDown_shadow, alpha_menu) }.start()
    }

    fun closeMenu() {
        this.isOpened = false
        val scaleUp_activity = buildScaleUpAnimation(this.viewActivity, 1.0f, 1.0f)
        val scaleUp_shadow = buildScaleUpAnimation(this.iv_shadow, 1.0f, 1.0f)
        val alpha_menu = buildMenuAnimation(this.llMenu, 0.0f)

        scaleUp_activity.addListener(this.animationListener)
        scaleUp_activity.also { it.playTogether(scaleUp_shadow, alpha_menu) }.start()
    }

    private fun initValue(activity: Activity) {
        this.activity = activity
        this.viewDecor = activity.window.decorView as ViewGroup
        this.viewActivity = TouchDisableView(this.activity)

        val mContent = this.viewDecor.getChildAt(0)
        this.viewDecor.removeViewAt(0)
        this.viewActivity.content = mContent
        // TODO: 6/9/17 I cant bring the viewActivity to the front.
        this.addView(this.viewActivity)

        (this.vScrollMenu.parent as ViewGroup).removeView(this.vScrollMenu)
    }

    private val animationListener = object: Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            if (this@SideMenu.isOpened) {
                this@SideMenu.showScrollViewMenu(this@SideMenu.llMenu)
                this@SideMenu.menuListener?.openMenu()
            }
        }

        override fun onAnimationEnd(animation: Animator) {
            if (this@SideMenu.isOpened) {
                this@SideMenu.viewActivity.touchDisabled = true
                this@SideMenu.viewActivity.setOnClickListener { if (this@SideMenu.isOpened) this@SideMenu.closeMenu() }
            }
            else {
                this@SideMenu.viewActivity.touchDisabled = false
                this@SideMenu.viewActivity.setOnClickListener(null)
                this@SideMenu.hideScrollViewMenu(this@SideMenu.llMenu)
                this@SideMenu.menuListener?.closeMenu()
            }
        }

        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {}
    }

    private fun setShadowAdjustScaleXByOrientation() = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            this.shadowAdjustScaleX = 0.034f
            this.shadowAdjustScaleY = 0.12f
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            this.shadowAdjustScaleX = 0.05f
            this.shadowAdjustScaleY = 0.06f
        }
        else -> TODO("noop")
    }

    private fun rebuildMenu() {
        this.llMenu.removeAllViews()
        this.menuItems.forEach { this.llMenu.addView(it) }
    }

    private fun setScaleDirection() {
        val (pivotX, pivotY) = Pair(screenWidth * 2.85f, screenHeight * 0.5f)

        this.viewActivity.pivotX = pivotX
        this.viewActivity.pivotY = pivotY
        this.iv_shadow.pivotX = pivotX * 1.1f
        this.iv_shadow.pivotY = pivotY * 1.1f
    }

    private fun buildScaleDownAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet =
        // scaleDownAnimation
        AnimatorSet().also {
            it.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))

            if (this.mUse3D) {
                it.playTogether(ObjectAnimator.ofFloat(target, "rotationY", -1 * ROTATE_Y_ANGLE))
            }

            it.interpolator = AnimationUtils.loadInterpolator(this.activity, android.R.anim.decelerate_interpolator)
            it.duration = ANIMATION_DURATION
        }

    private fun buildScaleUpAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet =
        // scaleAnimation
        AnimatorSet().also {
            it.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))

            if (this.mUse3D) {
                it.playTogether(ObjectAnimator.ofFloat(target, "rotationY", 0f))
            }

            it.duration = ANIMATION_DURATION
        }

    private fun buildMenuAnimation(target: View, alpha: Float): AnimatorSet =
        // alphaAnimation
        AnimatorSet().also {
            it.playTogether(ObjectAnimator.ofFloat(target, "alpha", alpha))
            it.duration = ANIMATION_DURATION
        }

    private fun isInIgnoredView(ev: MotionEvent): Boolean {
        val rect = Rect()

        this.ignoredViews.forEach {
            it.getGlobalVisibleRect(rect)
            if (rect.contains(ev.x.toInt(), ev.y.toInt())) {
                return true
            }
        }

        return false
    }

    private fun getTargetScale(currentRawX: Float): Float {
        val scaleFloatX = (currentRawX - lastRawX) / screenWidth * 0.5f
        var targetScale = this.viewActivity.scaleX - scaleFloatX

        targetScale = if (1.0f < targetScale) 1.0f else targetScale
        targetScale = if (0.5f > targetScale) 0.5f else targetScale

        return targetScale
    }

    private fun showScrollViewMenu(scrollViewMenu: View) {
        if (null == scrollViewMenu.parent) {
            this.addView(scrollViewMenu)
        }
    }

    private fun hideScrollViewMenu(scrollViewMenu: View) {
        if (null == scrollViewMenu.parent) {
            this.removeView(scrollViewMenu)
        }
    }
}