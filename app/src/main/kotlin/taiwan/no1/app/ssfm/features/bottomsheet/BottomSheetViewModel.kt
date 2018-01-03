package taiwan.no1.app.ssfm.features.bottomsheet

import android.annotation.SuppressLint
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import io.reactivex.internal.operators.observable.ObservableTimer
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.DebounceFlag
import taiwan.no1.app.ssfm.misc.utilies.devices.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import java.util.concurrent.TimeUnit

/**
 * @author  jieyi
 * @since   2017/12/17
 */

class BottomSheetViewModel(private val bsHelper: BottomSheetBehavior<View>,
                           private val addPlaylistItemCase: AddPlaylistItemCase) : BaseViewModel() {
    var obtainMusicUri: String? = null
    var openDialog = {}
    private var flag = DebounceFlag(false)
    private var debounce = false

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

    @SuppressLint("CheckResult")
    fun onBottomSheetAddToPlaylist(view: View) {
        if (!debounce) {
            debounce = true
            ObservableTimer(300, TimeUnit.MILLISECONDS, Schedulers.newThread()).subscribe({
                hideBottomSheet(view)
                openDialog()
            }, {}, { debounce = false })
        }
//        debounce(flag) {
//            hideBottomSheet(view)
//            openDialog()
//        }
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