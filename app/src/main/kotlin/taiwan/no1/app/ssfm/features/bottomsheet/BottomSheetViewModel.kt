package taiwan.no1.app.ssfm.features.bottomsheet

import android.support.design.widget.BottomSheetBehavior
import android.view.View
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase

/**
 * @author  jieyi
 * @since   2017/12/17
 */

class BottomSheetViewModel(private val bsHelper: BottomSheetBehavior<View>,
                           private val addPlaylistItemCase: AddPlaylistItemCase) : BaseViewModel() {
    var obtainMusicUri: String? = null
    var openDialog = {}

    fun onBottomSheetDownloadClick(view: View) {
        hideBottomSheet(view)
        obtainMusicUri?.let {
            MusicPlayerHelper.instance.run {
                downloadMusic(it)
                // TODO(jieyi): 2017/12/21 Add downloading task into the download activity.
                addBufferPercentageListeners { }
            }
        } ?: throw Exception("Please Implement Correct Music Uri...")
    }

    fun onBottomSheetAddToPlaylist(view: View) {
        hideBottomSheet(view)
        openDialog()
    }

    fun onBottomSheetShare(view: View) {
        hideBottomSheet(view)
    }

    fun onBottomSheetCancel(view: View) {
        hideBottomSheet(view)
    }

    private fun hideBottomSheet(view: View) {
        bsHelper.state = BottomSheetBehavior.STATE_HIDDEN
    }
}