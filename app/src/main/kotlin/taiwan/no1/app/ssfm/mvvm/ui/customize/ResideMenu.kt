package taiwan.no1.app.ssfm.mvvm.ui.customize

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.DisplayMetrics
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import taiwan.no1.app.ssfm.R
import java.util.*

class ResideMenu: FrameLayout {
    companion object {
        val DIRECTION_LEFT = 0
        val DIRECTION_RIGHT = 1
        private val PRESSED_MOVE_HORIZONTAL = 2
        private val PRESSED_DOWN = 3
        private val PRESSED_DONE = 4
        private val PRESSED_MOVE_VERTICAL = 5
        private val ROTATE_Y_ANGLE = 10
    }

    private var imageViewShadow: ImageView? = null
    private var imageViewBackground: ImageView? = null
    private var layoutLeftMenu: LinearLayout? = null
    private var layoutRightMenu: LinearLayout? = null
    /**
     * Returns left menu view so you can findViews and do whatever you want with
     */
    var leftMenuView: View? = null
        private set
    /**
     * Returns right menu view so you can findViews and do whatever you want with
     */
    var rightMenuView: View? = null
        private set
    private var scrollViewMenu: View? = null
    /**
     * Current attaching activity.
     */
    private var activity: Activity? = null
    /**
     * The DecorView of current activity.
     */
    private var viewDecor: ViewGroup? = null
    private var viewActivity: TouchDisableView? = null
    /**
     * The flag of menu opening status.
     */
    /**
     * return the flag of menu status;

     * @return
     */
    var isOpened: Boolean = false
        private set
    private var shadowAdjustScaleX: Float = 0.toFloat()
    private var shadowAdjustScaleY: Float = 0.toFloat()
    private var ignoredViews: MutableList<View>? = null
    private var leftMenuItems: MutableList<MenuItem>? = null
    private var rightMenuItems: MutableList<MenuItem>? = null
    private val displayMetrics = DisplayMetrics()
    var menuListener: OnMenuListener? = null
    private var lastRawX: Float = 0.toFloat()
    private var isInIgnoredView = false
    private var scaleDirection = DIRECTION_LEFT
    private var pressedState = PRESSED_DOWN
    private val disabledSwipeDirection = ArrayList<Int>()
    private var mScaleValue = 0.5f

    private var mUse3D: Boolean = false

    constructor(context: Context): super(context) {
        initViews(context, -1, -1)
    }

    /**
     * This constructor provides you to create menus with your own custom
     * layouts, but if you use custom menu then do not call addMenuItem because
     * it will not be able to find default views
     */
    constructor(context: Context, customLeftMenuId: Int,
                customRightMenuId: Int): super(context) {
        initViews(context, customLeftMenuId, customRightMenuId)
    }

    private fun initViews(context: Context, customLeftMenuId: Int,
                          customRightMenuId: Int) {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.residemenu_custom, this)

        if (customLeftMenuId >= 0) {
            leftMenuView = inflater.inflate(customLeftMenuId, this, false)
        }
        else {
            leftMenuView = inflater.inflate(
                R.layout.residemenu_custom_left_scrollview, this, false)
            layoutLeftMenu = leftMenuView!!.findViewById(R.id.layout_left_menu) as LinearLayout
        }

        if (customRightMenuId >= 0) {
            rightMenuView = inflater.inflate(customRightMenuId, this, false)
        }
        else {
            rightMenuView = inflater.inflate(
                R.layout.residemenu_custom_right_scrollview, this, false)
            layoutRightMenu = rightMenuView!!.findViewById(R.id.layout_right_menu) as LinearLayout
        }

        imageViewShadow = findViewById(R.id.iv_shadow) as ImageView
        imageViewBackground = findViewById(R.id.iv_background) as ImageView

        val menuHolder = findViewById(R.id.sv_menu_holder) as RelativeLayout
        menuHolder.addView(leftMenuView)
        menuHolder.addView(rightMenuView)
    }

    override fun fitSystemWindows(insets: Rect): Boolean {
        // Applies the content insets to the view's padding, consuming that
        // content (modifying the insets to be 0),
        // and returning true. This behavior is off by default and can be
        // enabled through setFitsSystemWindows(boolean)
        // in api14+ devices.

        // This is added to fix soft navigationBar's overlapping to content above LOLLIPOP
        var bottomPadding = viewActivity!!.paddingBottom + insets.bottom
        val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)
        val hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME)
        if (!hasBackKey || !hasHomeKey) {
            //there's a navigation bar
            bottomPadding += navigationBarHeight
        }

        this.setPadding(viewActivity!!.paddingLeft + insets.left,
            viewActivity!!.paddingTop + insets.top,
            viewActivity!!.paddingRight + insets.right,
            bottomPadding)
        insets.bottom = 0
        insets.right = insets.bottom
        insets.top = insets.right
        insets.left = insets.top
        return true
    }

    private val navigationBarHeight: Int
        get() {
            val resources = resources
            val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                return resources.getDimensionPixelSize(resourceId)
            }
            return 0
        }

    /**
     * Set up the activity;

     * @param activity
     */
    fun attachToActivity(activity: Activity) {
        initValue(activity)
        setShadowAdjustScaleXByOrientation()
        viewDecor!!.addView(this, 0)
    }

    private fun initValue(activity: Activity) {
        this.activity = activity
        leftMenuItems = ArrayList<MenuItem>()
        rightMenuItems = ArrayList<MenuItem>()
        ignoredViews = ArrayList<View>()
        viewDecor = activity.window.decorView as ViewGroup
        viewActivity = TouchDisableView(this.activity)

        val mContent = viewDecor!!.getChildAt(0)
        viewDecor!!.removeViewAt(0)
        viewActivity!!.content = mContent
        addView(viewActivity)

        val parent = leftMenuView!!.parent as ViewGroup
        parent.removeView(leftMenuView)
        parent.removeView(rightMenuView)
    }

    private fun setShadowAdjustScaleXByOrientation() {
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            shadowAdjustScaleX = 0.034f
            shadowAdjustScaleY = 0.12f
        }
        else if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            shadowAdjustScaleX = 0.06f
            shadowAdjustScaleY = 0.07f
        }
    }

    /**
     * Set the background image of menu;

     * @param imageResource
     */
    fun setBackground(imageResource: Int) {
        imageViewBackground!!.setImageResource(imageResource)
    }

    /**
     * The visibility of the shadow under the activity;

     * @param isVisible
     */
    fun setShadowVisible(isVisible: Boolean) {
        if (isVisible)
            imageViewShadow!!.setBackgroundResource(R.drawable.shadow)
        else
            imageViewShadow!!.setBackgroundResource(0)
    }

    /**
     * Add a single item to the left menu;
     *
     *
     * WARNING: It will be removed from v2.0.

     * @param menuItem
     */
    @Deprecated("")
    fun addMenuItem(menuItem: MenuItem) {
        this.leftMenuItems!!.add(menuItem)
        layoutLeftMenu!!.addView(menuItem)
    }

    /**
     * Add a single items;

     * @param menuItem
     * *
     * @param direction
     */
    fun addMenuItem(menuItem: MenuItem, direction: Int) {
        if (direction == DIRECTION_LEFT) {
            this.leftMenuItems!!.add(menuItem)
            layoutLeftMenu!!.addView(menuItem)
        }
        else {
            this.rightMenuItems!!.add(menuItem)
            layoutRightMenu!!.addView(menuItem)
        }
    }

    /**
     * Set menu items by a array;

     * @param menuItems
     * *
     * @param direction
     */
    fun setMenuItems(menuItems: MutableList<MenuItem>, direction: Int) {
        if (direction == DIRECTION_LEFT)
            this.leftMenuItems = menuItems
        else
            this.rightMenuItems = menuItems
        rebuildMenu()
    }

    private fun rebuildMenu() {
        if (layoutLeftMenu != null) {
            layoutLeftMenu!!.removeAllViews()
            for (leftMenuItem in leftMenuItems!!)
                layoutLeftMenu!!.addView(leftMenuItem)
        }

        if (layoutRightMenu != null) {
            layoutRightMenu!!.removeAllViews()
            for (rightMenuItem in rightMenuItems!!)
                layoutRightMenu!!.addView(rightMenuItem)
        }
    }

    /**
     * WARNING: It will be removed from v2.0.

     * @return
     */
    /**
     * WARNING: It will be removed from v2.0.

     * @param menuItems
     */
    var menuItems: MutableList<MenuItem>
        @Deprecated("")
        get() = leftMenuItems
        @Deprecated("")
        set(menuItems) {
            this.leftMenuItems = menuItems
            rebuildMenu()
        }

    /**
     * Return instances of menu items;

     * @return
     */
    fun getMenuItems(direction: Int): List<MenuItem> {
        if (direction == DIRECTION_LEFT)
            return leftMenuItems
        else
            return rightMenuItems
    }

    /**
     * Show the menu;
     */
    fun openMenu(direction: Int) {

        setScaleDirection(direction)

        isOpened = true
        val scaleDown_activity = buildScaleDownAnimation(viewActivity, mScaleValue, mScaleValue)
        val scaleDown_shadow = buildScaleDownAnimation(imageViewShadow,
            mScaleValue + shadowAdjustScaleX, mScaleValue + shadowAdjustScaleY)
        val alpha_menu = buildMenuAnimation(scrollViewMenu, 1.0f)
        scaleDown_shadow.addListener(animationListener)
        scaleDown_activity.playTogether(scaleDown_shadow)
        scaleDown_activity.playTogether(alpha_menu)
        scaleDown_activity.start()
    }

    /**
     * Close the menu;
     */
    fun closeMenu() {

        isOpened = false
        val scaleUp_activity = buildScaleUpAnimation(viewActivity, 1.0f, 1.0f)
        val scaleUp_shadow = buildScaleUpAnimation(imageViewShadow, 1.0f, 1.0f)
        val alpha_menu = buildMenuAnimation(scrollViewMenu, 0.0f)
        scaleUp_activity.addListener(animationListener)
        scaleUp_activity.playTogether(scaleUp_shadow)
        scaleUp_activity.playTogether(alpha_menu)
        scaleUp_activity.start()
    }

    @Deprecated("")
    fun setDirectionDisable(direction: Int) {
        disabledSwipeDirection.add(direction)
    }

    fun setSwipeDirectionDisable(direction: Int) {
        disabledSwipeDirection.add(direction)
    }

    private fun isInDisableDirection(direction: Int): Boolean {
        return disabledSwipeDirection.contains(direction)
    }

    private fun setScaleDirection(direction: Int) {

        val screenWidth = screenWidth
        val pivotX: Float
        val pivotY = screenHeight * 0.5f

        if (direction == DIRECTION_LEFT) {
            scrollViewMenu = leftMenuView
            pivotX = screenWidth * 1.5f
        }
        else {
            scrollViewMenu = rightMenuView
            pivotX = screenWidth * -0.5f
        }

        ViewHelper.setPivotX(viewActivity, pivotX)
        ViewHelper.setPivotY(viewActivity, pivotY)
        ViewHelper.setPivotX(imageViewShadow, pivotX)
        ViewHelper.setPivotY(imageViewShadow, pivotY)
        scaleDirection = direction
    }

    private val viewActivityOnClickListener = View.OnClickListener { if (isOpened) closeMenu() }

    private val animationListener = object: Animator.AnimatorListener() {
        fun onAnimationStart(animation: Animator) {
            if (isOpened) {
                showScrollViewMenu(scrollViewMenu)
                if (menuListener != null)
                    menuListener!!.openMenu()
            }
        }

        fun onAnimationEnd(animation: Animator) {
            // reset the view;
            if (isOpened) {
                viewActivity!!.setTouchDisable(true)
                viewActivity!!.setOnClickListener(viewActivityOnClickListener)
            }
            else {
                viewActivity!!.setTouchDisable(false)
                viewActivity!!.setOnClickListener(null)
                hideScrollViewMenu(leftMenuView)
                hideScrollViewMenu(rightMenuView)
                if (menuListener != null)
                    menuListener!!.closeMenu()
            }
        }

        fun onAnimationCancel(animation: Animator) {

        }

        fun onAnimationRepeat(animation: Animator) {

        }
    }

    /**
     * A helper method to build scale down animation;

     * @param target
     * *
     * @param targetScaleX
     * *
     * @param targetScaleY
     * *
     * @return
     */
    private fun buildScaleDownAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet {

        val scaleDown = AnimatorSet()
        scaleDown.playTogether(
            ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY)
        )

        if (mUse3D) {
            val angle = if (scaleDirection == DIRECTION_LEFT) -ROTATE_Y_ANGLE else ROTATE_Y_ANGLE
            scaleDown.playTogether(ObjectAnimator.ofFloat(target, "rotationY", angle))
        }

        scaleDown.interpolator = AnimationUtils.loadInterpolator(activity,
            android.R.anim.decelerate_interpolator)
        scaleDown.duration = 250
        return scaleDown
    }

    /**
     * A helper method to build scale up animation;

     * @param target
     * *
     * @param targetScaleX
     * *
     * @param targetScaleY
     * *
     * @return
     */
    private fun buildScaleUpAnimation(target: View, targetScaleX: Float, targetScaleY: Float): AnimatorSet {

        val scaleUp = AnimatorSet()
        scaleUp.playTogether(
            ObjectAnimator.ofFloat(target, "scaleX", targetScaleX),
            ObjectAnimator.ofFloat(target, "scaleY", targetScaleY)
        )

        if (mUse3D) {
            scaleUp.playTogether(ObjectAnimator.ofFloat(target, "rotationY", 0))
        }

        scaleUp.duration = 250
        return scaleUp
    }

    private fun buildMenuAnimation(target: View, alpha: Float): AnimatorSet {

        val alphaAnimation = AnimatorSet()
        alphaAnimation.playTogether(
            ObjectAnimator.ofFloat(target, "alpha", alpha)
        )

        alphaAnimation.duration = 250
        return alphaAnimation
    }

    /**
     * If there were some view you don't want reside menu
     * to intercept their touch event, you could add it to
     * ignored views.

     * @param v
     */
    fun addIgnoredView(v: View) {
        ignoredViews!!.add(v)
    }

    /**
     * Remove a view from ignored views;

     * @param v
     */
    fun removeIgnoredView(v: View) {
        ignoredViews!!.remove(v)
    }

    /**
     * Clear the ignored view list;
     */
    fun clearIgnoredViewList() {
        ignoredViews!!.clear()
    }

    /**
     * If the motion event was relative to the view
     * which in ignored view list,return true;

     * @param ev
     * *
     * @return
     */
    private fun isInIgnoredView(ev: MotionEvent): Boolean {
        val rect = Rect()
        for (v in ignoredViews!!) {
            v.getGlobalVisibleRect(rect)
            if (rect.contains(ev.x.toInt(), ev.y.toInt()))
                return true
        }
        return false
    }

    private fun setScaleDirectionByRawX(currentRawX: Float) {
        if (currentRawX < lastRawX)
            setScaleDirection(DIRECTION_RIGHT)
        else
            setScaleDirection(DIRECTION_LEFT)
    }

    private fun getTargetScale(currentRawX: Float): Float {
        var scaleFloatX = (currentRawX - lastRawX) / screenWidth * 0.75f
        scaleFloatX = if (scaleDirection == DIRECTION_RIGHT) -scaleFloatX else scaleFloatX

        var targetScale = ViewHelper.getScaleX(viewActivity) - scaleFloatX
        targetScale = if (targetScale > 1.0f) 1.0f else targetScale
        targetScale = if (targetScale < 0.5f) 0.5f else targetScale
        return targetScale
    }

    private var lastActionDownX: Float = 0.toFloat()
    private var lastActionDownY: Float = 0.toFloat()

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        val currentActivityScaleX = ViewHelper.getScaleX(viewActivity)
        if (currentActivityScaleX == 1.0f)
            setScaleDirectionByRawX(ev.rawX)

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                lastActionDownX = ev.x
                lastActionDownY = ev.y
                isInIgnoredView = isInIgnoredView(ev) && !isOpened
                pressedState = PRESSED_DOWN
            }

            MotionEvent.ACTION_MOVE -> {
                if (isInIgnoredView || isInDisableDirection(scaleDirection))
                    break

                if (pressedState != PRESSED_DOWN && pressedState != PRESSED_MOVE_HORIZONTAL)
                    break

                val xOffset = (ev.x - lastActionDownX).toInt()
                val yOffset = (ev.y - lastActionDownY).toInt()

                if (pressedState == PRESSED_DOWN) {
                    if (yOffset > 25 || yOffset < -25) {
                        pressedState = PRESSED_MOVE_VERTICAL
                        break
                    }
                    if (xOffset < -50 || xOffset > 50) {
                        pressedState = PRESSED_MOVE_HORIZONTAL
                        ev.action = MotionEvent.ACTION_CANCEL
                    }
                }
                else if (pressedState == PRESSED_MOVE_HORIZONTAL) {
                    if (currentActivityScaleX < 0.95)
                        showScrollViewMenu(scrollViewMenu)

                    val targetScale = getTargetScale(ev.rawX)
                    if (mUse3D) {
                        var angle = if (scaleDirection == DIRECTION_LEFT) -ROTATE_Y_ANGLE else ROTATE_Y_ANGLE
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
                if (pressedState != PRESSED_MOVE_HORIZONTAL) break

                pressedState = PRESSED_DONE
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

    val screenHeight: Int
        get() {
            activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.heightPixels
        }

    val screenWidth: Int
        get() {
            activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
            return displayMetrics.widthPixels
        }

    fun setScaleValue(scaleValue: Float) {
        this.mScaleValue = scaleValue
    }

    fun setUse3D(use3D: Boolean) {
        mUse3D = use3D
    }

    interface OnMenuListener {

        /**
         * This method will be called at the finished time of opening menu animations.
         */
        fun openMenu()

        /**
         * This method will be called at the finished time of closing menu animations.
         */
        fun closeMenu()
    }

    private fun showScrollViewMenu(scrollViewMenu: View?) {
        if (scrollViewMenu != null && scrollViewMenu.parent == null) {
            addView(scrollViewMenu)
        }
    }

    private fun hideScrollViewMenu(scrollViewMenu: View?) {
        if (scrollViewMenu != null && scrollViewMenu.parent != null) {
            removeView(scrollViewMenu)
        }
    }

}