package taiwan.no1.app.ssfm.misc.extension

/**
 * Collections extension tool.
 *
 * @author  jieyi
 * @since   9/6/17
 */
fun <E> ArrayList<E>.removeRange(from: Int, to: Int): Boolean =
    if (!(from in 0..size - 1 && to in 0..size - 1 && from > to)) {
        false
    }
    else {
        subList(from, to).clear()
        true
    }