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
import java.util.*


/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class SideMenu: FrameLayout {
    lateinit private var activity: Activity
    lateinit private var viewActivity: TouchDisableView
    lateinit private var viewDecor: ViewGroup

    var menuItems: MutableList<MenuItem> = mutableListOf()
        get() = field
        set(value) {
            field = value
            this.rebuildMenu()
        }
    lateinit var vScrollMenu: View
//    private val llMenu: LinearLayout by lazy { this.vScrollMenu.ll_menu }


    var isOpened: Boolean = false
        private set
    private var shadowAdjustScaleX: Float = 0.toFloat()
    private var shadowAdjustScaleY: Float = 0.toFloat()
    private var ignoredViews: MutableList<View>? = null
    private var leftMenuItems: MutableList<MenuItem>? = null
    private var rightMenuItems: MutableList<MenuItem>? = null
    private val displayMetrics = DisplayMetrics()
    var menuListener: ResideMenu.OnMenuListener? = null
    private var lastRawX: Float = 0.toFloat()
    private var isInIgnoredView = false
    //    private var pressedState = ResideMenu.PRESSED_DOWN
    private val disabledSwipeDirection = ArrayList<Int>()
    private var lastActionDownX: Float = 0.toFloat()
    private var lastActionDownY: Float = 0.toFloat()

    var mScaleValue = 0.5f
    var mUse3D: Boolean = false

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
        scaleDown_activity.playTogether(scaleDown_shadow)
        scaleDown_activity.playTogether(alpha_menu)
        scaleDown_activity.start()
    }

    fun closeMenu() {
        this.isOpened = false
        val scaleUp_activity = buildScaleUpAnimation(viewActivity, 1.0f, 1.0f)
        val scaleUp_shadow = buildScaleUpAnimation(this.iv_shadow, 1.0f, 1.0f)
        val alpha_menu = buildMenuAnimation(this.ll_menu, 0.0f)
        scaleUp_activity.addListener(animationListener)
        scaleUp_activity.playTogether(scaleUp_shadow)
        scaleUp_activity.playTogether(alpha_menu)
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

    private fun isInDisableDirection(direction: Int): Boolean = disabledSwipeDirection.contains(direction)

    private fun setScaleDirection() {
        val screenWidth = screenWidth
        val pivotX = screenWidth * 1.5f
        val pivotY = screenHeight * 0.5f

        ViewHelper.setPivotX(this.viewActivity, pivotX)
        ViewHelper.setPivotY(this.viewActivity, pivotY)
        ViewHelper.setPivotX(this.iv_shadow, pivotX)
        ViewHelper.setPivotY(this.iv_shadow, pivotY)
    }

    private val viewActivityOnClickListener = View.OnClickListener { if (isOpened) closeMenu() }

    private val animationListener = object: Animator.AnimatorListener {
        override fun onAnimationStart(animation: Animator) {
            if (this@SideMenu.isOpened) {
                this@SideMenu.showScrollViewMenu(this@SideMenu.ll_menu)
                this@SideMenu.menuListener?.let { it.openMenu() }
            }
        }

        override fun onAnimationEnd(animation: Animator) {
            if (isOpened) {
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

        if (mUse3D) {
            val angle = -ResideMenu.ROTATE_Y_ANGLE
            scaleDown.playTogether(ObjectAnimator.ofFloat(target, "rotationY", angle))
        }

        scaleDown.interpolator = AnimationUtils.loadInterpolator(activity, android.R.anim.decelerate_interpolator)
        scaleDown.duration = 250

        return scaleDown
    }

    private fun buildScaleUpAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet {

        val scaleUp = AnimatorSet()
        scaleUp.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))

        if (mUse3D) {
            scaleUp.playTogether(ObjectAnimator.ofFloat(target, "rotationY", 0f))
        }

        scaleUp.duration = 250

        return scaleUp
    }

    private fun buildMenuAnimation(target: View, alpha: Float): AnimatorSet {
        val alphaAnimation = AnimatorSet()
        alphaAnimation.playTogether(ObjectAnimator.ofFloat(target, "alpha", alpha))
        alphaAnimation.duration = 250

        return alphaAnimation
    }

    private fun isInIgnoredView(ev: MotionEvent): Boolean {
        val rect = Rect()

        this.ignoredViews?.forEach {
            it.getGlobalVisibleRect(rect)
            if (rect.contains(ev.x.toInt(), ev.y.toInt()))
                return true
        }

        return false
    }

    // FIXME: 6/7/17 modify!!!
    private fun setScaleDirectionByRawX(currentRawX: Float) {
        if (currentRawX < lastRawX)
            setScaleDirection(ResideMenu.DIRECTION_RIGHT)
        else
            setScaleDirection(ResideMenu.DIRECTION_LEFT)
    }

    private fun getTargetScale(currentRawX: Float): Float {
        var scaleFloatX = (currentRawX - lastRawX) / screenWidth * 0.75f

        var targetScale = ViewHelper.getScaleX(viewActivity) - scaleFloatX
        targetScale = if (targetScale > 1.0f) 1.0f else targetScale
        targetScale = if (targetScale < 0.5f) 0.5f else targetScale

        return targetScale
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentActivityScaleX = ViewHelper.getScaleX(viewActivity)
        if (currentActivityScaleX == 1.0f)
            setScaleDirectionByRawX(ev.rawX)

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastActionDownX = ev.x
                lastActionDownY = ev.y
                isInIgnoredView = isInIgnoredView(ev) && !isOpened
                pressedState = ResideMenu.PRESSED_DOWN
            }

            MotionEvent.ACTION_MOVE -> {
                if (isInIgnoredView || isInDisableDirection(scaleDirection))
                    break

                if (pressedState != ResideMenu.PRESSED_DOWN && pressedState != ResideMenu.PRESSED_MOVE_HORIZONTAL)
                    break

                val xOffset = (ev.x - lastActionDownX).toInt()
                val yOffset = (ev.y - lastActionDownY).toInt()

                if (pressedState == ResideMenu.PRESSED_DOWN) {
                    if (yOffset > 25 || yOffset < -25) {
                        pressedState = ResideMenu.PRESSED_MOVE_VERTICAL
                        break
                    }
                    if (xOffset < -50 || xOffset > 50) {
                        pressedState = ResideMenu.PRESSED_MOVE_HORIZONTAL
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                }
                else if (pressedState == ResideMenu.PRESSED_MOVE_HORIZONTAL) {
                    if (currentActivityScaleX < 0.95)
                        showScrollViewMenu(scrollViewMenu)

                    val targetScale = getTargetScale(ev.rawX)
                    if (mUse3D) {
                        var angle = if (scaleDirection == ResideMenu.DIRECTION_LEFT) -ResideMenu.ROTATE_Y_ANGLE else ResideMenu.ROTATE_Y_ANGLE
                        angle *= ((1 - targetScale) * 2).toInt()
                        ViewHelper.setRotationY(viewActivity, angle)

                        ViewHelper.setScaleX(imageViewShadow, targetScale - shadowAdjustScaleX)
                        ViewHelper.setScaleY(imageViewShadow, targetScale - shadowAdjustScaleY)
                    }
                    else {
                        ViewHelper.setScaleX(imageViewShadow, targetScale + shadowAdjustScaleX)
                        ViewHelper.setScaleY(imageViewShadow, targetScale + shadowAdjustScaleY)
                    }
                    ViewHelper.setScaleX(viewActivity, targetScale)
                    ViewHelper.setScaleY(viewActivity, targetScale)
                    ViewHelper.setAlpha(scrollViewMenu, (1 - targetScale) * 2.0f)

                    lastRawX = ev.rawX
                    return true
                }
            }

            MotionEvent.ACTION_UP -> {

                if (isInIgnoredView) break
                if (pressedState != ResideMenu.PRESSED_MOVE_HORIZONTAL) break

                pressedState = ResideMenu.PRESSED_DONE
                if (isOpened) {
                    if (currentActivityScaleX > 0.56f)
                        closeMenu()
                    else
                        openMenu(scaleDirection)
                }
                else {
                    if (currentActivityScaleX < 0.94f) {
                        openMenu(scaleDirection)
                    }
                    else {
                        closeMenu()
                    }
                }
            }
        }
        lastRawX = ev.rawX

        return super.dispatchTouchEvent(ev)
    }

    private fun showScrollViewMenu(scrollViewMenu: View) = scrollViewMenu.parent?.let { this.addView(scrollViewMenu) }

    private fun hideScrollViewMenu(scrollViewMenu: View) = scrollViewMenu.parent?.let { this.removeView(scrollViewMenu) }
}