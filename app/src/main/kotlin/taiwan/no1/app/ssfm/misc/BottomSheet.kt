package taiwan.no1.app.ssfm.misc

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import kotlinx.android.synthetic.main.bottomsheet_track.view.tv_add_playlist
import kotlinx.android.synthetic.main.bottomsheet_track.view.tv_cancel
import kotlinx.android.synthetic.main.bottomsheet_track.view.tv_download
import kotlinx.android.synthetic.main.bottomsheet_track.view.tv_share
import org.jetbrains.anko.sdk25.coroutines.onClick

/**
 * @author  jieyi
 * @since   2017/12/15
 */
const val BOTTOMSHEET_DOWNLOAD = 1
const val BOTTOMSHEET_TO_PLAYLIST = 2
const val BOTTOMSHEET_SHARE = 3
const val BOTTOMSHEET_CANCEL = 4

fun View.onBottomSheetClickItem(onClickCallback: (view: View?, which: Int) -> Unit) {
    if (!tv_download.hasOnClickListeners()) tv_download.onBottomSheetCancelClick(this@onBottomSheetClickItem) {
        onClickCallback(it, BOTTOMSHEET_DOWNLOAD)
    }
    if (!tv_add_playlist.hasOnClickListeners()) tv_add_playlist.onBottomSheetCancelClick(this@onBottomSheetClickItem) {
        onClickCallback(it, BOTTOMSHEET_TO_PLAYLIST)
    }
    if (!tv_share.hasOnClickListeners()) tv_share.onBottomSheetCancelClick(this@onBottomSheetClickItem) {
        onClickCallback(it, BOTTOMSHEET_SHARE)
    }
    if (!tv_cancel.hasOnClickListeners()) tv_cancel.onBottomSheetCancelClick(this@onBottomSheetClickItem) {
        onClickCallback(it, BOTTOMSHEET_CANCEL)
    }
}

private fun View.onBottomSheetCancelClick(view: View?, callback: (view: View?) -> Unit) = onClick {
    BottomSheetBehavior.from(view).state = BottomSheetBehavior.STATE_HIDDEN
    callback(this@onBottomSheetCancelClick)
}
