package taiwan.no1.app.ssfm.misc.extension

import android.annotation.SuppressLint
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * @author  jieyi
 * @since   2018/01/03
 */
@SuppressLint("CheckResult")
fun <T> createDebounce(interval: Long = 300, block: (param: T) -> Unit) =
    PublishSubject.create<T>().apply { debounce(interval, TimeUnit.MILLISECONDS).subscribe(block) }