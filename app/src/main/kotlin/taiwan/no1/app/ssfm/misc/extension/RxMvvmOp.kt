package taiwan.no1.app.ssfm.misc.extension

import android.annotation.SuppressLint
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

/**
 * @author  jieyi
 * @since   2018/01/03
 */
@SuppressLint("CheckResult")
fun <T> createDebounce(block: (param: T) -> Unit) =
    PublishSubject.create<T>().apply { debounce(300, TimeUnit.MILLISECONDS).subscribe(block) }