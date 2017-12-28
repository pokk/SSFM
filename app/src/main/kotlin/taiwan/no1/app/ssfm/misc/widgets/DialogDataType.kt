package taiwan.no1.app.ssfm.misc.widgets

import android.content.DialogInterface
import android.view.View

/**
 * @author  jieyi
 * @since   11/14/17
 */
typealias DialogFragmentBtn = Pair<String, (DialogInterface, Int) -> Unit>
typealias DSFListeners = ArrayList<Pair<Int, (QuickSupportDialogFragment, View?) -> Unit>>
typealias DFListeners = ArrayList<Pair<Int, (QuickDialogFragment, View?) -> Unit>>
typealias DFBListeners<B> = ArrayList<Pair<Int, (QuickDialogBindingFragment<B>, View?) -> Unit>>
typealias DSFBListeners<B> = ArrayList<Pair<Int, (QuickSupportDialogBindingFragment<B>, View?) -> Unit>>
