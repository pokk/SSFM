package taiwan.no1.app.ssfm.misc.extension

/**
 * Time extension tool.
 *
 * @author  jieyi
 * @since   7/13/17
 */
fun Int.format(digits: Int) = String.format("%0${digits}d", this)

inline fun Int.toTimeString(): String = "${(this / 60).format(2)}:${(this % 60).format(2)}"
