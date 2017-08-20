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
//class MainFragment: AdvancedFragment<FragmentViewModel, FragmentMainBinding>() {
//    override var viewModel: FragmentViewModel = FragmentViewModel()
//
//    override fun init(savedInstanceState: Bundle?) {
//        // TODO(jieyi): 8/21/17 This might be common into parent class.
//        this.binding.viewmodel = this.viewModel
//    }
//
//    override fun provideInflateView(): Int = R.layout.fragment_main
//
//    @Inject lateinit var ttt: String
//
//    fun init() {
//
//    }

class MainFragment: BaseFragment() {
    @Inject lateinit var ttt: String

    override fun init(savedInstanceState: Bundle?) {
        tv_no1.text = ttt
    }

    override fun provideInflateView(): Int = R.layout.fragment_main
}