package taiwan.no1.app.ssfm.features.bottomsheet

import android.annotation.SuppressLint
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.devrapid.kotlinknifer.mvvm.createDebounce
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.MusicPlayerHelper
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity

/**
 * @author  jieyi
 * @since   2017/12/17
 */
@SuppressLint("CheckResult")
class BottomSheetViewModel(private val bsHelper: BottomSheetBehavior<View>) : BaseViewModel() {
    var obtainMusicEntity: BaseEntity? = null
    private val debounceDownload by lazy {
        createDebounce<View> {
            hideBottomSheet(it)
            when (obtainMusicEntity) {
                is MusicEntity.Music -> (obtainMusicEntity as MusicEntity.Music).url
                is MusicRankEntity.Song -> (obtainMusicEntity as MusicRankEntity.Song).url
                else -> ""
            }.let {
                MusicPlayerHelper.instance.apply {
                    downloadMusic(it)
                    // TODO(jieyi): 2017/12/21 Add downloading task into the download activity.
                }
            }
        }
    }
    /**
     * @event_to [taiwan.no1.app.ssfm.features.chart.ChartActivity.openPlaylistDialog]
     */
    private val debounceOpenDialog by lazy {
        createDebounce<View> {
            hideBottomSheet(it)
            RxBus.get().post(VIEWMODEL_CLICK_PLAYLIST_FRAGMENT_DIALOG, obtainMusicEntity)
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