package taiwan.no1.app.ssfm.mvvm.views.fragments

import android.os.Bundle
import kotlinx.android.synthetic.main.fragment_main.tv_no1
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.mvvm.views.BaseFragment
import javax.inject.Inject

/**
 *
 * @author  jieyi
 * @since   8/20/17
 */
class MainFragment: BaseFragment() {
    @Inject lateinit var ttt: String

    override fun init(savedInstanceState: Bundle?) {
        tv_no1.text = ttt
    }

    override fun inflateView(): Int = R.layout.fragment_main
}