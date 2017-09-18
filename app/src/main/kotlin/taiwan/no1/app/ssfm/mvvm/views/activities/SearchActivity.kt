package taiwan.no1.app.ssfm.mvvm.views.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.part_toolbar_search.iv_content_hamburger
import kotlinx.android.synthetic.main.part_toolbar_search.sv_search
import kotlinx.android.synthetic.main.part_toolbar_search.tv_menu_title
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.databinding.ActivitySearchBinding
import taiwan.no1.app.ssfm.mvvm.viewmodels.SearchViewModel
import taiwan.no1.app.ssfm.mvvm.views.AdvancedActivity
import javax.inject.Inject


/**
 *
 * @author  jieyi
 * @since   9/16/17
 */
class SearchActivity: AdvancedActivity<SearchViewModel, ActivitySearchBinding>() {
    @Inject override lateinit var viewModel: SearchViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO(jieyi): 9/18/17 I can't find searchview event(onClose, onOpen) in xml.
        sv_search.setOnCloseListener {
            View.VISIBLE.let {
                tv_menu_title.visibility = it
                iv_content_hamburger.visibility = it
            }
            false
        }
        sv_search.setOnSearchClickListener {
            View.GONE.let {
                tv_menu_title.visibility = it
                iv_content_hamburger.visibility = it
            }
        }
        // TODO(jieyi): 9/18/17 Set the hint icon gone.
        val magId = resources.getIdentifier("search_mag_icon", "id", "android")
        val magImage = sv_search.findViewById<ImageView>(magId)
        magImage.visibility = View.GONE
    }

    override fun provideBindingLayoutId(): Pair<Activity, Int> = Pair(this, R.layout.activity_search)
}