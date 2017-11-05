package taiwan.no1.app.ssfm.misc.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager


/**
 * Keyboard extension tool.
 *
 * @author  jieyi
 * @since   9/19/17
 */
fun Context.hideSoftKeyboard() =
    (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
        toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)

fun View.hideSoftKeyboard() =
    (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
        hideSoftInputFromWindow(windowToken, 0)

fun View.showSoftKeyboard() =
    apply {
        isFocusable = true
        isFocusableInTouchMode = true
        requestFocus()
        (context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).
            showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }
