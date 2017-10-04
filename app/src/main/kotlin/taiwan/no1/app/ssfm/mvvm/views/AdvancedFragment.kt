package taiwan.no1.app.ssfm.mvvm.views

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.mvvm.viewmodels.IViewModel
import java.lang.reflect.ParameterizedType

/**
 * Advanced [BaseFragment] with [IViewModel] and [ViewDataBinding] for basic data binding setting in advance.
 *
 * @author  jieyi
 * @since   8/20/17
 */
abstract class AdvancedFragment<VM: IViewModel, B: ViewDataBinding>: BaseFragment() {
    protected abstract var viewModel: VM
    protected lateinit var binding: B
    // Data type of the parameters.
    private val genericVMClass: Class<*>
        get() = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>

    //region Activity lifecycle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.onAttach(this)
    }

    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, provideInflateView(), container, false)
        // HACK(jieyi): 8/21/17 Using reflection here, the performance might become lower. Maybe there are some better ways to do.
        binding.javaClass.getMethod("setVm", genericVMClass).invoke(binding, viewModel)

        return binding.root
    }

    override fun onDestroy() {
        viewModel.onDetach()
        logw(binding)
        binding.javaClass.getMethod("setVm", genericVMClass).invoke(binding, null)
        super.onDestroy()
    }
    //endregion
}
