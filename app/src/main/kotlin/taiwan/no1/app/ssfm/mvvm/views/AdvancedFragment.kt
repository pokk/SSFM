package taiwan.no1.app.ssfm.mvvm.views

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import taiwan.no1.app.ssfm.mvvm.viewmodels.IViewModel

/**
 * Advanced [BaseFragment] with [IViewModel] and [ViewDataBinding] for basic data binding setting in advance.
 *
 * @author  jieyi
 * @since   8/20/17
 */
abstract class AdvancedFragment<VM: IViewModel, B: ViewDataBinding>: BaseFragment() {
    protected abstract var viewModel: VM
    protected lateinit var binding: B

    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, this.provideInflateView(), container, false)

        return this.binding.root
    }
}
