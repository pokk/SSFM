package taiwan.no1.app.ssfm.mvvm.view

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.RxFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import javax.inject.Inject

/**
 * Base fragment for collecting all common methods here.
 *
 * @author  jieyi
 * @since   5/9/17
 */
abstract class BaseFragment: RxFragment(), HasFragmentInjector {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected var rootView: View? = null

    //region Fragment cycle
    override fun onAttach(context: Context) {
        AndroidInjection.inject(this)
        super.onAttach(context)
    }

    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {
        // Keep the instance data.
        this.retainInstance = true

        // FIXED: https://www.zybuluo.com/kimo/note/255244
        rootView ?: let { rootView = inflater.inflate(this.inflateView(), null) }
        val parent: ViewGroup? = rootView?.parent as ViewGroup?
        parent?.removeView(rootView)

        // Init the presenter.
        this.initPresenter()

        return rootView
    }

    override final fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.init(savedInstanceState)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
    }

    @CallSuper
    override fun onPause() {
        super.onPause()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }
    //endregion

    override fun fragmentInjector(): AndroidInjector<Fragment> = this.childFragmentInjector

    //region Initialization's order
    /**
     * Initialize the fragment method.
     *
     * @param savedInstanceState before status.
     */
    abstract protected fun init(savedInstanceState: Bundle?)

    /**
     * Set the view for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    @LayoutRes
    abstract protected fun inflateView(): Int

    /**
     * Initialize the presenter in [onCreateView].
     */
    abstract protected fun initPresenter()
    //endregion
}