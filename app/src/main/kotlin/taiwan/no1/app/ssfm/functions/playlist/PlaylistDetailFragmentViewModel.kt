package taiwan.no1.app.ssfm.functions.playlist

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import android.widget.ImageButton
import com.devrapid.kotlinknifer.loge
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.misc.extension.imageview.setSrc
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.models.usecases.GetPlaylistItemsUsecase

/**
 * @author  jieyi
 * @since   11/14/17
 */
class PlaylistDetailFragmentViewModel(private val editPlaylistUsecase: BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue>,
                                      private val getPlaylistItemsUsecase: BaseUsecase<List<PlaylistItemEntity>, GetPlaylistItemsUsecase.RequestValue>) :
    BaseViewModel() {
    val playlistName by lazy { ObservableField<String>() }
    val trackQuantity by lazy { ObservableField<String>() }
    val duration by lazy { ObservableField<String>() }
    val thumbnail by lazy { ObservableField<String>() }
    val isEditMode by lazy { ObservableBoolean(false) }
    private var playlistItem: PlaylistEntity? = null

    fun attachPlaylistInfo(playlist: PlaylistEntity) {
        playlist.let {
            playlistItem = it
            playlistName.set(it.name)
            trackQuantity.set(it.track_quantity.toString())
            thumbnail.set(it.image_uri)
        }
    }

    fun fetchPlaylistItems(playlistId: Long, callback: (List<PlaylistItemEntity>) -> Unit) {
        lifecycleProvider.execute(getPlaylistItemsUsecase, GetPlaylistItemsUsecase.RequestValue(playlistId)) {
            onNext(callback)
        }
    }

    fun editOnClick(view: View) {
        if (isEditMode.get()) {
            playlistItem?.let {
                it.name = playlistName.get()
                lifecycleProvider.execute(editPlaylistUsecase, AddPlaylistUsecase.RequestValue(it)) {
                    onNext { logw(it) }
                    onError { loge(it) }
                }
            }
        }
        isEditMode.set(!isEditMode.get())
        (view as ImageButton).setSrc(if (isEditMode.get()) R.drawable.ic_check else R.drawable.ic_edit)
    }
}