package taiwan.no1.app.ssfm.misc.widgets

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import org.jetbrains.anko.layoutInflater
import taiwan.no1.app.ssfm.R

/**
 *
 * @author  jieyi
 * @since   7/5/17
 */
class AdapterPager(val context: Context, val listView: List<Int>) : PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): View {
        return context.layoutInflater.inflate(R.layout.sample_button, null).also {
            //            it.iv_ppp.imageResource = listView[position]
            container.addView(it)
        }
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

    override fun getCount(): Int = listView.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)
}