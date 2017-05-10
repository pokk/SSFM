package taiwan.no1.app.ssfm.mvvm.ui

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Base fragment for collecting all common methods here.
 *
 * @author  jieyi
 * @since   5/9/17
 */
abstract class BaseFragment: RxFragment() {
    protected var rootView: View? = null

    //region Fragment lifecycle
    override final fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                                    savedInstanceState: Bundle?): View? {
        // Avoid that an activity is deleted and get null pointer so inject the component here.
        this.inject()
        // Keep the instance data.
        this.retainInstance = true

        // FIXED: https://www.zybuluo.com/kimo/note/255244
        if (null == rootView)
            rootView = inflater.inflate(this.inflateView(), null)
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

    //region Initialization's order
    /**
     * Initialize the fragment method.
     *
     * @param savedInstanceState before status.
     */
    abstract protected fun init(savedInstanceState: Bundle?)

    /**
     * Injected the presenter and the fragment use case.
     */
    abstract protected fun inject()

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