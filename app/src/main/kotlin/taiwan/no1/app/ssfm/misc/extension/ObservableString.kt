package taiwan.no1.app.ssfm.misc.extension

import android.databinding.BaseObservable
import java.io.Serializable

/**
 * Creates an empty observable object.
 *
 * @author  jieyi
 * @since   9/20/17
 */
open class ObservableString(): BaseObservable(), Serializable {
    private var mValue: String? = null

    /**
     * Wraps the given object and creates an observable object
     *
     * @param value The value to be wrapped as an observable.
     */
    constructor(value: String): this() {
        mValue = value
    }

    /**
     * @return the stored value.
     */
    fun get(): String? {
        return mValue
    }

    /**
     * Set the stored value.
     */
    fun set(value: String) {
        if (value !== mValue) {
            mValue = value
            notifyChange()
        }
    }

    companion object {
        internal const val serialVersionUID = 1L
    }
}