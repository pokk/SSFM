package taiwan.no1.app.ssfm.mvvm.views.recyclerview

import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.View
import com.devrapid.kotlinknifer.logw

/**
 *
 * @author  jieyi
 * @since   9/3/17
 */
class BaseViewHolder(itemView: View): ViewHolder(itemView) {
    fun initView(item: Any, position: Int, adapter: BaseAdapter) {
        this.itemView.setOnClickListener {
            logw("hello world", position, item)
            adapter.expand(position)
        }
    }
}