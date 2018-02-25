package taiwan.no1.app.ssfm.features.bottomsheet

import android.Manifest
import android.annotation.SuppressLint
import android.support.design.widget.BottomSheetBehavior
import android.view.View
import com.devrapid.kotlinknifer.mvvm.createDebounce
import com.hwangjr.rxbus.RxBus
import com.tbruyelle.rxpermissions2.RxPermissions
import com.trello.rxlifecycle2.LifecycleProvider
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_DOWNLOAD_ID
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.VIEWMODEL_CLICK_PLAYLIST_FRAGMENT_DIALOG
import taiwan.no1.app.ssfm.misc.utilies.devices.helper.music.playerHelper
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.entities.transforms.transformToPlaylist
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase

/**
 * @author  jieyi
 * @since   2017/12/17
 */
@SuppressLint("CheckResult")
class BottomSheetViewModel(
    private val lifecycle: LifecycleProvider<*>,
    private val permission: RxPermissions,
    private val bsHelper: BottomSheetBehavior<View>,
    private val addPlaylistItemCase: AddPlaylistItemCase
) : BaseViewModel() {
    var obtainMusicEntity: BaseEntity? = null
    private val debounceDownload by lazy {
        createDebounce<View> { v ->
            permission
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .compose(lifecycle.bindToLifecycle())
                .map {
                    if (it) {
                        hideBottomSheet(v)
                        when (obtainMusicEntity) {
                            is MusicEntity.Music -> (obtainMusicEntity as MusicEntity.Music).transformToPlaylist()
                            is MusicRankEntity.Song -> (obtainMusicEntity as MusicRankEntity.Song).transformToPlaylist()
                            is PlaylistItemEntity -> obtainMusicEntity as PlaylistItemEntity
                            else -> throw IllegalArgumentException("There is no kind of data type!")
                        }
                    }
                    else {
                        throw IllegalArgumentException("There is no kind of data type!")
                    }
                }
                .map { it.apply { it.id = DATABASE_PLAYLIST_DOWNLOAD_ID.toLong() } }
                .subscribe {
                    if (!playerHelper.downloadMusic(it.trackUri)) throw RuntimeException("Cannot write to the external storage.")

                    // TODO(jieyi): 2018/02/25 How add the download item to database!!!
                    addPlaylistItemCase.pureUsecase(AddPlaylistItemUsecase.RequestValue(it))
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

    init {
//        debounceDownload.subscribe {
//            logw("Hello world")
//        }
    }

    fun onBottomSheetDownloadClick(view: View) = debounceDownload.onNext(view)

    fun onBottomSheetAddToPlaylist(view: View) = debounceOpenDialog.onNext(view)

    fun onBottomSheetShare(view: View) = debounceShare.onNext(view)

    fun onBottomSheetCancel(view: View) = debounceCancel.onNext(view)

    private fun hideBottomSheet(view: View) {
        bsHelper.state = BottomSheetBehavior.STATE_HIDDEN
    }
}