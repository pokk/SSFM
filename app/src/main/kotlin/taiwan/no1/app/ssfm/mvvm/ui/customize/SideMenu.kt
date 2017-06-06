package taiwan.no1.app.ssfm.mvvm.ui.customize

import android.app.Activity
import android.content.Context
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import taiwan.no1.app.ssfm.R


/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class SideMenu: FrameLayout {
    lateinit private var activity: Activity
    lateinit private var viewDecor: ViewGroup

    lateinit private var viewMenu: View

    lateinit private var leftMenuItems: List<MenuItem>

    private val inflater: LayoutInflater
    private var isOpened: Boolean = false

    init {
        inflater = (this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).
                inflate(R.layout., this)
    }

    constructor(context: Context, @LayoutRes resMenu: Int): super(context)
}