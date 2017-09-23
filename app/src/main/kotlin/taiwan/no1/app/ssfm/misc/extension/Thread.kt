@file:Suppress("NOTHING_TO_INLINE")

package taiwan.no1.app.ssfm.misc.extension

/**
 * Thread extension tool.
 *
 * @author  jieyi
 * @since   9/23/17
 */
inline fun threadName(): String = Thread.currentThread().name