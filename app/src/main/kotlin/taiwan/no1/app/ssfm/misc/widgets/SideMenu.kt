package taiwan.no1.app.ssfm.misc.widgets

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
import com.devrapid.kotlinknifer.WeakRef
import com.devrapid.kotlinknifer.animatorListener
import kotlinx.android.synthetic.main.custom_menu_scroll_view.view.ll_menu
import kotlinx.android.synthetic.main.custom_menu_view_container.view.iv_background
import kotlinx.android.synthetic.main.custom_menu_view_container.view.iv_shadow
import kotlinx.android.synthetic.main.custom_menu_view_container.view.rl_menu_holder
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.R
import java.lang.ref.WeakReference

/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class SideMenu(context: Context, @LayoutRes resMenu: Int = -1) : FrameLayout(context) {
    companion object {
        private const val PRESSED_MOVE_HORIZONTAL = 2
        private const val PRESSED_MOVE_VERTICAL = 3
        private const val PRESSED_DOWN = 4
        private const val PRESSED_DONE = 5
        private const val ROTATE_Y_ANGLE = 10f
        private const val ANIMATION_DURATION = 250L
    }

    //region Variables
    // ** Public variables
    lateinit var vScrollMenu: View
    var isOpened = false
        private set
    var menuListener = MenuListener()
        private set
    var menuItems = mutableListOf<WeakReference<MenuItem>>()
        set(value) {
            field = value
            rebuildMenu()
        }
    var mScaleValue = 0.75f
    var mUse3D = false
    lateinit private var activity: Activity
    lateinit private var viewActivity: TouchDisableView
    lateinit private var viewDecor: ViewGroup
    // ** Private variables
    private val llMenu by lazy { vScrollMenu.ll_menu }
    private val displayMetrics by lazy { DisplayMetrics() }
    private val screenHeight by lazy {
        displayMetrics.let { activity.windowManager.defaultDisplay.getMetrics(it); it.heightPixels }
    }
    private val screenWidth by lazy {
        displayMetrics.let { activity.windowManager.defaultDisplay.getMetrics(it); it.widthPixels }
    }
    private val animationListener = animatorListener {
        onAnimationStart {
            if (this@SideMenu.isOpened) {
                this@SideMenu.showScrollViewMenu(this@SideMenu.llMenu)
                this@SideMenu.menuListener._openMenu?.invoke()
            }
        }
        onAnimationEnd {
            if (this@SideMenu.isOpened) {
                this@SideMenu.viewActivity.touchDisabled = true
                this@SideMenu.viewActivity.onClick { if (this@SideMenu.isOpened) this@SideMenu.closeMenu() }
            }
            else {
                this@SideMenu.viewActivity.touchDisabled = false
                this@SideMenu.viewActivity.setOnClickListener(null)
                this@SideMenu.hideScrollViewMenu(this@SideMenu.llMenu)
                this@SideMenu.menuListener._closeMenu?.invoke()
            }
        }
    }
    private var ignoredViews = mutableListOf<View>()
    private var shadowAdjustScaleX = 0f
    private var shadowAdjustScaleY = 0f
    private var lastRawX = 0f
    private var lastActionDownX = 0f
    private var lastActionDownY = 0f
    private var pressedState = PRESSED_DOWN
    private var isInIgnoredView = false
    //endregion

    init {
        (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).let {
            it.inflate(R.layout.custom_menu_view_container, this)

            vScrollMenu = it.inflate(if (0 <= resMenu) resMenu else R.layout.custom_menu_scroll_view,
                                     this,
                                     false)
            rl_menu_holder.addView(vScrollMenu)
        }
    }

    override fun dispatchTouchEvent(ev: android.view.MotionEvent): Boolean {
        val currentActivityScaleX = viewActivity.scaleX

        if (1.0f == currentActivityScaleX) {
            setScaleDirection()
        }

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastActionDownX = ev.x
                lastActionDownY = ev.y
                isInIgnoredView = isInIgnoredView(ev) && !isOpened
                pressedState = PRESSED_DOWN
            }
            MotionEvent.ACTION_MOVE -> run action_move@ {
                if ((PRESSED_DOWN != pressedState && PRESSED_MOVE_HORIZONTAL != pressedState) ||
                    isInIgnoredView) {
                    return@action_move
                }

                val (xOffset, yOffset) = Pair((ev.x - lastActionDownX).toInt(),
                                              (ev.y - lastActionDownY).toInt())

                if (PRESSED_DOWN == pressedState) {
                    if (25 < yOffset || -25 > yOffset) {
                        pressedState = PRESSED_MOVE_VERTICAL

                        return@action_move
                    }
                    if (30 < xOffset || -30 > xOffset) {
                        pressedState = PRESSED_MOVE_HORIZONTAL
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                }
                else if (PRESSED_MOVE_HORIZONTAL == pressedState) {
                    if (0.95f > currentActivityScaleX) {
                        showScrollViewMenu(vScrollMenu)
                    }

                    val targetScale = getTargetScale(ev.rawX)
                    if (mUse3D) {
                        val angle = (-1 * ROTATE_Y_ANGLE) * ((1 - targetScale) * 2).toInt()

                        viewActivity.rotationY = angle
                        iv_shadow.scaleX = targetScale - shadowAdjustScaleX
                        iv_shadow.scaleY = targetScale - shadowAdjustScaleY
                    }
                    else {
                        iv_shadow.scaleX = targetScale + shadowAdjustScaleX
                        iv_shadow.scaleY = targetScale + shadowAdjustScaleY
                    }
                    viewActivity.scaleX = targetScale
                    viewActivity.scaleY = targetScale

                    vScrollMenu.alpha = (1 - targetScale) * 4f

                    lastRawX = ev.rawX

                    return true
                }
            }
            MotionEvent.ACTION_UP -> run action_up@ {
                if (isInIgnoredView || PRESSED_MOVE_HORIZONTAL != pressedState) {
                    return@action_up
                }

                pressedState = PRESSED_DONE
                if (isOpened) {
                    if (0.79f < currentActivityScaleX) closeMenu() else openMenu()
                }
                else {
                    if (0.93f > currentActivityScaleX) openMenu() else closeMenu()
                }
            }
        }
        lastRawX = ev.rawX

        return super.dispatchTouchEvent(ev)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()

        menuItems.clear()
    }

    fun attachActivity(activity: Activity) {
        initValue(activity)
        setShadowAdjustScaleXByOrientation()
        viewDecor.addView(this, 0)
    }

    fun openMenu() {
        setScaleDirection()

        isOpened = true
        // Scale down for the activity.
        buildScaleDownAnimation(viewActivity, mScaleValue, mScaleValue).also {
            val scaleDown_shadow = buildScaleDownAnimation(iv_shadow,
                                                           mScaleValue + shadowAdjustScaleX,
                                                           mScaleValue + shadowAdjustScaleY)
            val alpha_menu = buildMenuAnimation(vScrollMenu, 1.0f)

            it.addListener(animationListener)
            it.playTogether(scaleDown_shadow, alpha_menu)
        }.start()
    }

    fun closeMenu() {
        isOpened = false
        // Scale up for the activity.
        buildScaleUpAnimation(viewActivity, 1.0f, 1.0f).also {
            val scaleUp_shadow = buildScaleUpAnimation(iv_shadow, 1.0f, 1.0f)
            val alpha_menu = buildMenuAnimation(vScrollMenu, 0.0f)

            it.addListener(animationListener)
            it.playTogether(scaleUp_shadow, alpha_menu)
        }.start()
    }

    fun setMenuBackground(@DrawableRes resBackground: Int) = iv_background.setImageResource(
        resBackground)

    fun setShadowVisible(isVisible: Boolean) = iv_shadow.setBackgroundResource(if (isVisible) R.drawable.shadow else 0)

    fun addMenuItem(menuItem: MenuItem) {
        menuItems.add(WeakReference(menuItem))
        llMenu.addView(menuItem)
    }

    fun addIgnoredView(v: View) = ignoredViews.add(v)

    fun removeIgnoredView(v: View) = ignoredViews.remove(v)

    fun clearIgnoredViewList() = ignoredViews.clear()

    private fun initValue(activity: Activity) {
        this.activity = activity
        viewDecor = activity.window.decorView as ViewGroup
        viewActivity = TouchDisableView(this.activity)

        val mContent = viewDecor.getChildAt(0)
        viewDecor.removeViewAt(0)
        viewActivity.content = mContent
        // TODO: 6/9/17 I cant bring the viewActivity to the front.
        addView(viewActivity)

        (vScrollMenu.parent as ViewGroup).removeView(vScrollMenu)
    }

    private fun setShadowAdjustScaleXByOrientation() = when (resources.configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            shadowAdjustScaleX = 0.034f
            shadowAdjustScaleY = 0.12f
        }
        Configuration.ORIENTATION_PORTRAIT -> {
            shadowAdjustScaleX = 0.05f
            shadowAdjustScaleY = 0.06f
        }
        else -> TODO("noop")
    }

    private fun rebuildMenu() {
        llMenu.removeAllViews()
        menuItems.forEach { llMenu.addView(it.get()) }
    }

    private fun setScaleDirection() {
        val (pivotX, pivotY) = Pair(screenWidth * 2.85f, screenHeight * 0.5f)

        viewActivity.pivotX = pivotX
        viewActivity.pivotY = pivotY
        iv_shadow.pivotX = pivotX * 1.1f
        iv_shadow.pivotY = pivotY * 1.1f
    }

    private fun buildScaleDownAnimation(target: View,
                                        targetScaleX: Float,
                                        targetScaleY: Float): AnimatorSet =
        // scale down animation
        AnimatorSet().also {
            it.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))

            if (mUse3D) {
                it.playTogether(ObjectAnimator.ofFloat(target, "rotationY", -1 * ROTATE_Y_ANGLE))
            }

            it.interpolator = AnimationUtils.loadInterpolator(activity,
                                                              android.R.anim.decelerate_interpolator)
            it.duration = ANIMATION_DURATION
        }

    private fun buildScaleUpAnimation(target: View,
                                      targetScaleX: Float,
                                      targetScaleY: Float): AnimatorSet =
        // scale animation
        AnimatorSet().also {
            it.playTogether(ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
                            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY))

            if (mUse3D) {
                it.playTogether(ObjectAnimator.ofFloat(target, "rotationY", 0f))
            }

            it.duration = ANIMATION_DURATION
        }

    private fun buildMenuAnimation(target: View, alpha: Float): AnimatorSet =
        // alpha animation
        AnimatorSet().also {
            it.playTogether(ObjectAnimator.ofFloat(target, "alpha", alpha))
            it.duration = ANIMATION_DURATION
        }

    private fun isInIgnoredView(ev: MotionEvent): Boolean {
        val rect = Rect()

        ignoredViews.forEach {
            it.getGlobalVisibleRect(rect)
            if (rect.contains(ev.x.toInt(), ev.y.toInt())) {
                return true
            }
        }

        return false
    }

    private fun getTargetScale(currentRawX: Float): Float {
        val scaleFloatX = (currentRawX - lastRawX) / screenWidth * 0.5f
        var targetScale = viewActivity.scaleX - scaleFloatX

        targetScale = if (1.0f < targetScale) 1.0f else targetScale
        targetScale = if (0.5f > targetScale) 0.5f else targetScale

        return targetScale
    }

    private fun showScrollViewMenu(scrollViewMenu: View) = scrollViewMenu.parent ?: let {
        addView(scrollViewMenu)
    }

    private fun hideScrollViewMenu(scrollViewMenu: View) = scrollViewMenu.parent ?: let {
        removeView(scrollViewMenu)
    }

    /**
     * Menu Listener.
     */
    class MenuListener {
        var _openMenu by WeakRef({})
        var _closeMenu by WeakRef({})

        internal fun openMenu(open: () -> Unit): MenuListener = also { it._openMenu = open }
        internal fun closeMenu(close: () -> Unit): MenuListener = also { it._closeMenu = close }
    }
}