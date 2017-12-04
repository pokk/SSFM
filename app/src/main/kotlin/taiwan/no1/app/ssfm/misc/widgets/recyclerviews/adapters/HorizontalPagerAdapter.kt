package taiwan.no1.app.ssfm.misc.widgets.recyclerviews.adapters

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup

/**
 * @author  jieyi
 * @since   2017/12/03
 */
class HorizontalPagerAdapter(private val items: List<View>) : PagerAdapter() {
    override fun getCount() = if (3 == items.size) items.size - 1 else items.size

    override fun instantiateItem(container: ViewGroup, position: Int) =
        items[position].let { container.addView(it) }
    // FIXME(jieyi): 2017/12/03 change to MVVM architecture.
//        val view: View = LayoutInflater.from(context).inflate(R.layout.item_album_type_1, container, false)
//        Glide.with(this@HorizontalPagerAdapter.context).
//            asBitmap().
//            load(items[position]).
//            apply(RequestOptions().apply {
//                fitCenter()
//                holderDrawable?.let(this::placeholder)
//                errorDrawable?.let(this::error)
//                priority(Priority.HIGH)
//                diskCacheStrategy(DiskCacheStrategy.RESOURCE)
//            }).into(view.iv_album_image)
//
//        container.addView(view)
//
//        return view
//    }

    override fun isViewFromObject(view: View, `object`: Any) = view == `object`

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)
}