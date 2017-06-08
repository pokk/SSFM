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
import taiwan.no1.app.ssfm.mvvm.ui.customize.ResideMenu.Companion.DIRECTION_LEFT
import taiwan.no1.app.ssfm.mvvm.ui.customize.ResideMenu.Companion.DIRECTION_RIGHT
import java.util.*


/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class SideMenu: FrameLayout {
    companion object {
        private const val PRESSED_MOVE_HORIZONTAL = 2
        private const val PRESSED_DOWN = 3
        private const val PRESSED_DONE = 4
        private const val PRESSED_MOVE_VERTICAL = 5
        private const val ROTATE_Y_ANGLE = 10f
    }

    lateinit private var activity: Activity
    lateinit private var viewActivity: TouchDisableView
    lateinit private var viewDecor: ViewGroup

    lateinit var vScrollMenu: View
//    private val llMenu: LinearLayout by lazy { this.vScrollMenu.ll_menu }


    var isOpened: Boolean = false
        private set
    private var shadowAdjustScaleX = 0f
    private var shadowAdjustScaleY = 0f
    private var ignoredViews = mutableListOf<View>()
    var menuItems = mutableListOf<MenuItem>()
        get() = field
        set(value) {
            field = value
            this.rebuildMenu()
        }
    private val displayMetrics = DisplayMetrics()
    var menuListener: OnMenuListener? = null
    private var lastRawX = 0f
    private var isInIgnoredView = false
    private var pressedState = PRESSED_DOWN
    private val disabledSwipeDirection = ArrayList<Int>()
    private var lastActionDownX = 0f
    private var lastActionDownY = 0f

    var mScaleValue = 0.5f
    var mUse3D = false

    val screenHeight: Int
        get() {
            this.activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

    val screenWidth: Int
        get() {
            this.activity.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

    interface OnMenuListener {
        fun openMenu()
        fun closeMenu()
    }

    init {
        (this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).let {
            it.inflate(R.layout.custom_menu_view, this)

            this.vScrollMenu = it.inflate(R.layout.custom_scroll_menu, this, false)
            this.rl_menu_holder.addView(this.vScrollMenu)
        }
    }

    constructor(context: Context, @LayoutRes resMenu: Int): super(context)

    fun attachActivity(activity: Activity) {
    }

    fun setBackground(@DrawableRes resBackground: Int) = this.iv_background.setImageResource(resBackground)

    fun setShadowVisible(isVisible: Boolean) = this.iv_shadow.setBackgroundResource(if (isVisible) R.drawable.shadow else 0)

    fun addMenuItem(menuItem: MenuItem) {
        this.menuItems.add(menuItem)
        this.ll_menu.addView(menuItem)
    }

    fun addIgnoredView(v: View) = this.ignoredViews.add(v)

    fun removeIgnoredView(v: View) = this.ignoredViews.remove(v)

    fun clearIgnoredViewList() = this.ignoredViews.clear()

    fun openMenu() {
        this.setScaleDirection()

        this.isOpened = true
        val scaleDown_activity = buildScaleDownAnimation(this.viewActivity, this.mScaleValue, this.mScaleValue)
        val scaleDown_shadow = buildScaleDownAnimation(this.iv_shadow,
            this.mScaleValue + this.shadowAdjustScaleX,
            this.mScaleValue + this.shadowAdjustScaleY)
        val alpha_menu = buildMenuAnimation(this.ll_menu, 1.0f)
        scaleDown_shadow.addListener(animationListener)
        scaleDown_activity.playTogether(scaleDown_shadow, alpha_menu)
//        scaleDown_activity.playTogether(alpha_menu)
        scaleDown_activity.start()
    }

    fun closeMenu() {
        this.isOpened = false
        val scaleUp_activity = buildScaleUpAnimation(viewActivity, 1.0f, 1.0f)
        val scaleUp_shadow = buildScaleUpAnimation(this.iv_shadow, 1.0f, 1.0f)
        val alpha_menu = buildMenuAnimation(this.ll_menu, 0.0f)
        scaleUp_activity.addListener(animationListener)
        scaleUp_activity.playTogether(scaleUp_shadow, alpha_menu)
//        scaleUp_activity.playTogether(alpha_menu)
        scaleUp_activity.start()
    }

    fun setSwipeDirectionDisable(direction: Int) = this.disabledSwipeDirection.add(direction)

    private fun setShadowAdjustScaleXByOrientation() = resources.configuration.orientation.let {
        when (it) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                this.shadowAdjustScaleX = 0.034f
                this.shadowAdjustScaleY = 0.12f
            }
            Configuration.ORIENTATION_PORTRAIT -> {
                this.shadowAdjustScaleX = 0.06f
                this.shadowAdjustScaleY = 0.07f
            }
        }
    }

    private fun rebuildMenu() {
        this.ll_menu.removeAllViews()
        this.menuItems.forEach { this.ll_menu.addView(it) }
    }

    private fun isInDisableDirection(direction: Int): Boolean = this.disabledSwipeDirection.contains(direction)

    private fun setScaleDirection() {
        val screenWidth = screenWidth
        val pivotX = screenWidth * 1.5f
        val pivotY = screenHeight * 0.5f

        this.viewActivity.pivotX = pivotX
        this.viewActivity.pivotY = pivotY
        this.iv_shadow.pivotX = pivotX
        this.iv_shadow.pivotY = pivotY
    }

    private val viewActivityOnClickListener = View.OnClickListener { if (this.isOpened) this.closeMenu() }

    private val animationListener = object: Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            if (this@SideMenu.isOpened) {
                this@SideMenu.showScrollViewMenu(this@SideMenu.ll_menu)
                this@SideMenu.menuListener?.let { it.openMenu() }
            }
        }

        override fun onAnimationEnd(animation: Animator) {
            if (this@SideMenu.isOpened) {
                this@SideMenu.viewActivity.setTouchDisable(true)
                this@SideMenu.viewActivity.setOnClickListener(this@SideMenu.viewActivityOnClickListener)
            }
            else {
                this@SideMenu.viewActivity.setTouchDisable(false)
                this@SideMenu.viewActivity.setOnClickListener(null)
                this@SideMenu.hideScrollViewMenu(this@SideMenu.ll_menu)
                this@SideMenu.menuListener?.let { it.closeMenu() }
            }
        }

        override fun onAnimationCancel(animation: Animator) {}

        override fun onAnimationRepeat(animation: Animator) {}
    }

    private fun buildScaleDownAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet {
        val scaleDown = AnimatorSet()

        scaleDown.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))

        if (this.mUse3D) {
            scaleDown.playTogether(ObjectAnimator.ofFloat(target, "rotationY", -1 * ROTATE_Y_ANGLE))
        }

        scaleDown.interpolator = AnimationUtils.loadInterpolator(this.activity, android.R.anim.decelerate_interpolator)
        scaleDown.duration = 250

        return scaleDown
    }

    private fun buildScaleUpAnimation(target: View,
                                      targetScaleX: Float,
                                      targetScaleY: Float): AnimatorSet = AnimatorSet().also {

        val scaleUp = AnimatorSet()
//        
//        scaleUp.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
//            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))
//
//        if (this.mUse3D) {
//            scaleUp.playTogether(ObjectAnimator.ofFloat(target, "rotationY", 0f))
//        }
//
//        scaleUp.duration = 250
//
//        return scaleUp
    }

//    private fun buildScaleUpAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet {
//        val scaleUp = AnimatorSet()
//
//        scaleUp.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
//            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))
//
//        if (this.mUse3D) {
//            scaleUp.playTogether(ObjectAnimator.ofFloat(target, "rotationY", 0f))
//        }
//
//        scaleUp.duration = 250
//
//        return scaleUp
//    }

    private fun buildMenuAnimation(target: View, alpha: Float): AnimatorSet {
        val alphaAnimation = AnimatorSet()

        alphaAnimation.playTogether(ObjectAnimator.ofFloat(target, "alpha", alpha))
        alphaAnimation.duration = 250

        return alphaAnimation
    }

    private fun isInIgnoredView(ev: MotionEvent): Boolean {
        val rect = Rect()

        this.ignoredViews.forEach {
            it.getGlobalVisibleRect(rect)
            if (rect.contains(ev.x.toInt(), ev.y.toInt()))
                return true
        }

        return false
    }

    // FIXME: 6/7/17 modify!!!
    private fun setScaleDirectionByRawX(currentRawX: Float) {
        if (currentRawX < lastRawX) setScaleDirection(DIRECTION_RIGHT)
        else setScaleDirection(DIRECTION_LEFT)
    }

    private fun getTargetScale(currentRawX: Float): Float {
        var scaleFloatX = (currentRawX - lastRawX) / screenWidth * 0.75f

        var targetScale = this.viewActivity.scaleX - scaleFloatX
        targetScale = if (targetScale > 1.0f) 1.0f else targetScale
        targetScale = if (targetScale < 0.5f) 0.5f else targetScale

        return targetScale
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentActivityScaleX = this.viewActivity.scaleX
        if (1.0f == currentActivityScaleX)
            this.setScaleDirectionByRawX(ev.rawX)

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastActionDownX = ev.x
                lastActionDownY = ev.y
                this.isInIgnoredView = this.isInIgnoredView(ev) && !this.isOpened
                this.pressedState = PRESSED_DOWN
            }
            MotionEvent.ACTION_MOVE -> {
                if (this.isInIgnoredView || this.isInDisableDirection(scaleDirection))
                    break

                if (this.pressedState != PRESSED_DOWN && this.pressedState != PRESSED_MOVE_HORIZONTAL)
                    break

                val xOffset = (ev.x - lastActionDownX).toInt()
                val yOffset = (ev.y - lastActionDownY).toInt()

                if (this.pressedState == PRESSED_DOWN) {
                    if (yOffset > 25 || yOffset < -25) {
                        this.pressedState = PRESSED_MOVE_VERTICAL
                        break
                    }
                    if (xOffset < -50 || xOffset > 50) {
                        this.pressedState = PRESSED_MOVE_HORIZONTAL
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                }
                else if (this.pressedState == PRESSED_MOVE_HORIZONTAL) {
                    if (currentActivityScaleX < 0.95) showScrollViewMenu(this.vScrollMenu)

                    val targetScale = getTargetScale(ev.rawX)
                    if (this.mUse3D) {
                        var angle = (-1 * ROTATE_Y_ANGLE) * ((1 - targetScale) * 2).toInt()
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
                    this.vScrollMenu.alpha = (1 - targetScale) * 2.0f

                    lastRawX = ev.rawX

                    return true
                }
            }
            MotionEvent.ACTION_UP -> {
                if (isInIgnoredView || this.pressedState != PRESSED_MOVE_HORIZONTAL)
                    break

                this.pressedState = PRESSED_DONE
                if (this.isOpened) {
                    if (currentActivityScaleX > 0.56f) this.closeMenu() else this.openMenu()
                }
                else {
                    if (currentActivityScaleX < 0.94f) this.openMenu() else this.closeMenu()
                }
            }
        }
        lastRawX = ev.rawX

        return super.dispatchTouchEvent(ev)
    }

    private fun showScrollViewMenu(scrollViewMenu: View) = scrollViewMenu.parent?.let { this.addView(scrollViewMenu) }

    private fun hideScrollViewMenu(scrollViewMenu: View) = scrollViewMenu.parent?.let { this.removeView(scrollViewMenu) }
}