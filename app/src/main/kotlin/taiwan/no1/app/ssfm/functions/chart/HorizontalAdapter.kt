package taiwan.no1.app.ssfm.functions.chart

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_album_type_1.view.iv_album_image
import taiwan.no1.app.ssfm.R


/**
 * @author  jieyi
 * @since   2017/12/03
 */
class HorizontalPagerAdapter(private val context: Context, private val items: List<String>) : PagerAdapter() {
    override fun getCount() = items.size

    override fun getItemPosition(`object`: Any?) = POSITION_NONE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        // FIXME(jieyi): 2017/12/03 change to MVVM architecture.
        val view: View = LayoutInflater.from(context).inflate(R.layout.item_album_type_1, container, false)
//        observable<RequestBuilder<Bitmap>> {
        Glide.with(this@HorizontalPagerAdapter.context).
            asBitmap().
            load(items[position]).
            apply(RequestOptions().apply {
                fitCenter()
//                holderDrawable?.let(this::placeholder)
//                errorDrawable?.let(this::error)
                priority(Priority.HIGH)
                diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            }).into(view.iv_album_image)
//        }.subscribeOn(Schedulers.io()).
//            observeOn(AndroidSchedulers.mainThread()).
//            subscribe { it.into(view.iv_album_image) }

        container.addView(view)

        return view
    }

    override fun isViewFromObject(view: View, `object`: Any) = view.equals(`object`)

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) =
        container.removeView(`object` as View)
}