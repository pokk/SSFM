package taiwan.no1.app.ssfm.misc.widgets

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.item_custom_menu.view.tv_title
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class MenuItem(context: Context) : LinearLayout(context) {
    var tv_menu: TextView

    init {
        val view = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
            .inflate(R.layout.item_custom_menu, this)
        tv_menu = view.tv_title
    }

    constructor(context: Context, @DrawableRes resIcon: Int, @StringRes resTitle: Int) :
        this(context) {
        tv_menu.setText(resTitle)
        tv_menu.setCompoundDrawablesWithIntrinsicBounds(resIcon, 0, 0, 0)
    }

    constructor(context: Context, @DrawableRes resIcon: Int, title: String) : this(context) {
        tv_menu.text = title
        tv_menu.setCompoundDrawablesWithIntrinsicBounds(resIcon, 0, 0, 0)
    }
}