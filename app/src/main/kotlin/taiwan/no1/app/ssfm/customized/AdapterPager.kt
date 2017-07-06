package taiwan.no1.app.ssfm.customized

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.test_customize_view.view.*
import org.jetbrains.anko.imageResource
import org.jetbrains.anko.layoutInflater
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   7/5/17
 */
class AdapterPager(val context: Context, val listView: List<Int>): PagerAdapter() {

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        return this.context.layoutInflater.inflate(R.layout.test_customize_view, null).also {
            it.iv_ppp.imageResource = listView[position]
            container.addView(it)
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = this.listView.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)
}