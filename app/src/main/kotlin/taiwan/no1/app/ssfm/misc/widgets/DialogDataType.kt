package taiwan.no1.app.ssfm.misc.widgets

import android.content.DialogInterface
import android.view.View

/**
 * @author  jieyi
 * @since   11/14/17
 */
typealias DialogFragmentBtn = Pair<String, (DialogInterface, Int) -> Unit>
typealias DialogSupportFragmentListeners = ArrayList<Pair<Int, (QuickSupportDialogFragment, View?) -> Unit>>
typealias DialogFragmentListeners = ArrayList<Pair<Int, (QuickDialogFragment, View?) -> Unit>>
