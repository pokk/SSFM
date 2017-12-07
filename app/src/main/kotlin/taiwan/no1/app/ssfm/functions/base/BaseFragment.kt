package taiwan.no1.app.ssfm.functions.base

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Build
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
abstract class BaseFragment : RxFragment(), HasFragmentInjector {
    /** From an activity for providing to children searchFragments. */
    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    protected open var rootView: View? = null

    //region Fragment cycle
    /** Perform injection here before M, L (API 22) and below because this is not yet available at L. */
    @SuppressWarnings("deprecation")
    override fun onAttach(activity: Activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            AndroidInjection.inject(this)
        }
        super.onAttach(activity)
    }

    /** Perform injection here for M (API 23) due to deprecation of onAttach(Activity). */
    override fun onAttach(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndroidInjection.inject(this)
        }
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Keep the instance data.
        retainInstance = true
        // FIXED: https://www.zybuluo.com/kimo/note/255244
        rootView ?: let { rootView = inflater.inflate(provideInflateView(), null) }
        val parent: ViewGroup? = rootView?.parent as ViewGroup?
        parent?.removeView(rootView)

        return rootView
    }

    override final fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rendered(savedInstanceState)
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

    //region Base activity implement
    /**
     * The fragment's view is rendered so from this method we can do some operations for views, as like,
     * set listeners or animations...etc.
     * This method will be called each of time when the fragment reappears.
     *
     * @param savedInstanceState before status.
     */
    abstract protected fun rendered(savedInstanceState: Bundle?)

    /**
     * Set the view for inflating.
     *
     * @return [LayoutRes] layout xml.
     */
    @LayoutRes
    abstract protected fun provideInflateView(): Int
    //endregion

    /**
     * Providing the fragment injector([Fragment]) for this children of searchFragments.
     *
     * @return a [fragmentInjector] for children of this fragment.
     */
    override fun fragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector
}