package taiwan.no1.app.ssfm.mvvm.ui.customize

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.custom_menu_item.view.*
import taiwan.no1.app.ssfm.R


/**
 *
 * @author  jieyi
 * @since   6/7/17
 */
class MenuItem(context: Context): LinearLayout(context) {
    var iv_icon: ImageView
    var tv_menu: TextView

    init {
        val view = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).
            inflate(R.layout.custom_menu_item, this)
        this.iv_icon = view.iv_icon
        this.tv_menu = view.tv_title
    }

    constructor(context: Context, @DrawableRes resIcon: Int, @StringRes resTitle: Int): this(context) {
        this.iv_icon.setImageResource(resIcon)
        this.tv_menu.setText(resTitle)
    }

    constructor(context: Context, @DrawableRes resIcon: Int, title: String): this(context) {
        this.iv_icon.setImageResource(resIcon)
        this.tv_menu.text = title
    }
}