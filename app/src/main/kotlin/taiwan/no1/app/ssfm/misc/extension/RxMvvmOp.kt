package taiwan.no1.app.ssfm.misc.extension

import android.annotation.SuppressLint
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * @author  jieyi
 * @since   2018/01/03
 */
/** Wrapper for passing by reference and letting us modify the flag variable. */
data class DebounceFlag(var flag: Boolean)

@SuppressLint("CheckResult")
inline fun debounce(debounce: DebounceFlag, crossinline block: () -> Unit) {
    if (!debounce.flag) {
        debounce.flag = true
        ObservableTimer(300, TimeUnit.MILLISECONDS, Schedulers.newThread()).
            subscribe({ block() }, {}, { debounce.flag = false })
    }
}