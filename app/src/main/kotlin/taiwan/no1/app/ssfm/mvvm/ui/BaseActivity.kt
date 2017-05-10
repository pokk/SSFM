package taiwan.no1.app.ssfm.mvvm.ui

import android.os.Bundle
import android.os.PersistableBundle
import android.support.annotation.CallSuper
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import taiwan.no1.app.ssfm.App
import taiwan.no1.app.ssfm.internal.di.components.AppComponent

/**
 * Base activity for collecting all common methods here.
 *
 * @author  jieyi
 * @since   5/9/17
 */
abstract class BaseActivity: RxAppCompatActivity() {

    //region Activity lifecycle
    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }

    @CallSuper
    override fun onResume() {
        super.onResume()
    }

    @CallSuper
    override fun onDestroy() {
        super.onDestroy()
    }
    //endregion

//    /**
//     * Get the [ActivityComponent] for injecting a presenter and a use case.
//     *
//     * @return [ActivityComponent]
//     */
//    protected fun getComponent(): ActivityComponent =
//            ActivityComponent.Initializer.init(this.getApplicationComponent())
//
//    /**
//     * Provide the [FragmentComponent] to fragments for injecting a presenter and use cases.
//     *
//     * @return [FragmentComponent]
//     */
//    protected fun provideFragmentComponent(): FragmentComponent =
//            FragmentComponent.Initializer.init(this.getApplicationComponent())

    /**
     * Get the Main Application component for dependency injection.
     *
     * @return [AppComponent]
     */
    protected fun getApplicationComponent(): AppComponent = App.appComponent()
}