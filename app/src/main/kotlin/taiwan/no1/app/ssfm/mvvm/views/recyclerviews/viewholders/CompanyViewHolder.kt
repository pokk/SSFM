package taiwan.no1.app.ssfm.mvvm.views.recyclerviews.viewholders

import android.view.View
import com.devrapid.kotlinknifer.logd
import kotlinx.android.synthetic.main.item_preference_first_layer_title.view.tv_title
import org.jetbrains.anko.sdk25.coroutines.onClick
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.BaseAdapter
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.BaseViewHolder
import taiwan.no1.app.ssfm.mvvm.views.recyclerviews.Company

/**
 * TEST
 *
 * @author  jieyi
 * @since   9/5/17
 */
class CompanyViewHolder(view: View): BaseViewHolder<Company>(view) {
    override fun initView(model: Company, position: Int, adapter: BaseAdapter) {
        super.initView(model, position, adapter)

        this.itemView.tv_title.text = "position: $position"
        this.itemView.onClick { adapter.expand(position) }
        logd("Again", position)
    }
}