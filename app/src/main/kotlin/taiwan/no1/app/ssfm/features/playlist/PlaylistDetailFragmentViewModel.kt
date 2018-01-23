package taiwan.no1.app.ssfm.features.playlist

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import android.widget.ImageButton
import com.devrapid.kotlinknifer.hideSoftKeyboard
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import com.devrapid.kotlinknifer.showSoftKeyboard
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag.OPEN_SERVICE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.imageview.setSrc
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistItemUsecase
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.EditPlaylistCase
import taiwan.no1.app.ssfm.models.usecases.FetchPlaylistItemCase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * @author  jieyi
 * @since   11/14/17
 */
class PlaylistDetailFragmentViewModel(private val editPlaylistUsecase: EditPlaylistCase,
                                      private val getPlaylistItemsUsecase: FetchPlaylistItemCase,
                                      private val removePlaylistItemUsecase: AddPlaylistItemCase) : BaseViewModel() {
    val playlistName by lazy { ObservableField<String>() }
    val trackQuantity by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val isEditMode by lazy { ObservableBoolean(false) }
    private var playlistItem: PlaylistEntity? = null

    fun attachPlaylistInfo(playlist: PlaylistEntity) {
        playlist.let {
            val pluralOfTrack = if (it.trackQuantity > 1) "s" else ""

            playlistItem = it
            playlistName.set(it.name)
            trackQuantity.set("${it.trackQuantity} track$pluralOfTrack")
            thumbnail.set(it.imageUri)
        }
    }

    fun fetchPlaylistItems(playlistId: Long, callback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistItemsUsecase, GetPlaylistItemsUsecase.RequestValue(playlistId)) {
            onNext(callback)
        }
    }

    fun deleteItem(item: PlaylistItemEntity, callback: (Boolean) -> Unit) {
        lifecycleProvider.execute(removePlaylistItemUsecase, AddPlaylistItemUsecase.RequestValue(item)) {
            onNext(callback)
        }
    }

    fun editOnClick(view: View) {
        if (isEditMode.get()) {
            playlistItem?.let {
                it.name = playlistName.get().orEmpty()
                lifecycleProvider.execute(editPlaylistUsecase, AddPlaylistUsecase.RequestValue(it)) {
                    onNext { logw(it) }
                    onError { loge(it) }
                    onComplete { view.hideSoftKeyboard() }
                }
            }
        }
        else {
            view.requestFocus()
            view.showSoftKeyboard()
        }
        isEditMode.set(!isEditMode.get())
        (view as ImageButton).setSrc(if (isEditMode.get()) R.drawable.ic_check else R.drawable.ic_edit)
    }

    fun takePhoto(view: View) {
        RxBus.get().post(OPEN_SERVICE, "")
    }
}