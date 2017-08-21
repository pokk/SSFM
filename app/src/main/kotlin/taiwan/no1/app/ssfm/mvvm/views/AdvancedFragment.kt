package taiwan.no1.app.ssfm.mvvm.views

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {
        this.binding = DataBindingUtil.inflate(inflater, this.provideInflateView(), container, false)
        // HACK(jieyi): 8/21/17 Using reflection here, the performance might become lower. Maybe there are some better ways to do.
        this.binding.javaClass.getMethod("setViewmodel", genericVMClass).invoke(this.binding, this.viewModel)

        return this.binding.root
    }

    override fun onDestroy() {
        this.binding.javaClass.getMethod("setViewmodel", genericVMClass).invoke(this.binding, null)
        super.onDestroy()
    }
}
