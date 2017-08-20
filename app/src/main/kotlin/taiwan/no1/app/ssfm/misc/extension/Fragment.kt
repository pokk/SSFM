package taiwan.no1.app.ssfm.misc.extension

import android.app.Fragment
import android.app.FragmentManager
import android.os.Build.VERSION_CODES
import android.support.annotation.RequiresApi
import android.view.View

/**
 *
 * @author  jieyi
 * @since   8/19/17
 */

/**
 * Adds a [Fragment] to this manager's layout.
 *
 * @param containerViewId The container view to where add the fragment.
 * @param fragment The fragment to be added.
 * @param needBack Set that it can back to previous fragment.
 * @param sharedElements Shared element objects and ids from layout xml [android:transitionName].
 *
 * @return the identifier of this transaction's back stack entry.
 */
fun FragmentManager.addFragment(containerViewId: Int,
                                fragment: Fragment,
                                needBack: Boolean = false,
                                sharedElements: HashMap<View, String> = hashMapOf()): Int = this.beginTransaction().apply {
    replace(containerViewId, fragment, fragment.javaClass.name)
    sharedElements.forEach { value -> addSharedElement(value.key, value.value) }
    if (needBack)
        addToBackStack(fragment.javaClass.name)
}.commit()

/**
 * Pop a [Fragment] from the [FragmentManager].
 *
 * @return is success to pop a [Fragment].
 */
inline fun FragmentManager.popFragment(): Boolean = if (0 < this.backStackEntryCount) {
    this.popBackStackImmediate()
    true
}
else
    false

/**
 * Clear all [Fragment] in the stack.
 */
inline fun FragmentManager.popAllFragment() {
    // Optimized by Kotlin.
    for (i in 0..this.backStackEntryCount - 1) {
        this.popFragment()
    }
}

/**
 * Remove a specific [Fragment] from [FragmentManager] stack.
 *
 * @param fragment Specific assigned [Fragment].
 */
@RequiresApi(VERSION_CODES.N)
inline fun FragmentManager.removeFragment(fragment: Fragment) = this.beginTransaction().remove(fragment).commitNow()

// TODO(jieyi): 8/19/17 Cannot get fragments from FragmentManager.
/**
 * Remove all [Fragment] from [FragmentManager] stack.
 */
//fun FragmentManager.removeRecursiveFragment() = this.fragments?.forEach {
//    it?.let { f ->
//        it.childFragmentManager?.fragments?.forEach {
//            it?.let { f.childFragmentManager.removeFragment(it) }
//        }
//    }
//}

/**
 * Testing code. For showing all fragments and children fragments.
 */
//fun FragmentManager.showAllFragment() = this.fragments?.forEach {
//    it?.let {
//        Logs.v("parent : $it")
//        it.childFragmentManager?.fragments?.forEach { Logs.d("child!!!! : $it") }
//    }
//}
