package taiwan.no1.app.ssfm.mvvm.views

import android.app.Activity
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.CallSuper
import taiwan.no1.app.ssfm.mvvm.viewmodels.IViewModel
import java.lang.reflect.ParameterizedType

/**
 * Advanced [BaseActivity] with [IViewModel] and [ViewDataBinding] for basic data binding setting in advance.
 *
 * @author  jieyi
 * @since   5/10/17
 */
abstract class AdvancedActivity<VM: IViewModel, out B: ViewDataBinding>: BaseActivity() {
    protected abstract var viewModel: VM
    protected val binding: B by lazy {
        val (activity, layoutId) = this.provideBindingLayoutId()
        // DataBinding process.
        DataBindingUtil.setContentView<B>(activity, layoutId)
    }
    // Data type of the parameters.
    private val genericVMClass: Class<*>
        get() = (this::class.java.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<*>


    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // HACK(jieyi): 8/21/17 Using reflection here, the performance might become lower. Maybe there are some better ways to do.
        this.binding.javaClass.getMethod("setViewmodel", genericVMClass).invoke(this.binding, this.viewModel)
    }

    @CallSuper
    override fun onDestroy() {
        this.binding.javaClass.getMethod("setViewmodel", genericVMClass).invoke(this.binding, null)
        super.onDestroy()
    }

    abstract protected fun provideBindingLayoutId(): Pair<Activity, Int>
}
