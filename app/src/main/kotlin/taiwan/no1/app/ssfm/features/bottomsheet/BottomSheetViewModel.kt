package taiwan.no1.app.ssfm.features.bottomsheet

import android.annotation.SuppressLint
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.createDebounce
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase

/**
 * @author  jieyi
 * @since   2017/12/17
 */
@SuppressLint("CheckResult")
class BottomSheetViewModel(private val bsHelper: BottomSheetBehavior<View>,
                           private val addPlaylistItemCase: AddPlaylistItemCase) : BaseViewModel() {
    var obtainMusicUri: String? = null
    var openDialog = {}
    private val debounceDownload by lazy {
        createDebounce<View> {
            hideBottomSheet(it)
            obtainMusicUri?.let {
                MusicPlayerHelper.instance.run {
                    downloadMusic(it)
                    // TODO(jieyi): 2017/12/21 Add downloading task into the download activity.
                    addBufferPercentageListeners { }
                }
            } ?: throw Exception("Please Implement Correct Music Uri...")
        }
    }
    private val debounceOpenDialog by lazy {
        createDebounce<View> {
            hideBottomSheet(it)
            openDialog()
        }
    }
    private val debounceShare by lazy { createDebounce<View> { hideBottomSheet(it) } }
    private val debounceCancel by lazy { createDebounce<View> { hideBottomSheet(it) } }

    fun onBottomSheetDownloadClick(view: View) = debounceDownload.onNext(view)

    fun onBottomSheetAddToPlaylist(view: View) = debounceOpenDialog.onNext(view)

    fun onBottomSheetShare(view: View) = debounceShare.onNext(view)

    fun onBottomSheetCancel(view: View) = debounceCancel.onNext(view)

    private fun hideBottomSheet(view: View) {
        bsHelper.state = BottomSheetBehavior.STATE_HIDDEN
    }
}