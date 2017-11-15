package taiwan.no1.app.ssfm.functions.playlist

import android.content.Context
import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logw
import com.hwangjr.rxbus.RxBus
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.RxBusTag
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.usecases.AddPlaylistUsecase
import taiwan.no1.app.ssfm.models.usecases.BaseUsecase

/**
 *
 * @author  jieyi
 * @since   9/13/17
 */
class PlaylistViewModel(private val context: Context,
                        private val addPlaylistUsecase: BaseUsecase<Boolean, AddPlaylistUsecase.RequestValue>) :
    BaseViewModel() {
    val title by lazy { ObservableField<String>(context.getString(R.string.menu_my_playlist)) }

    /**
     * @param view
     *
     * @event_to [taiwan.no1.app.ssfm.functions.playlist.PlaylistActivity.navigateToPlaylistDetail]
     */
    fun addPlaylistOnClick(view: View?) {
        lifecycleProvider.execute(addPlaylistUsecase, AddPlaylistUsecase.RequestValue(PlaylistEntity())) {
            onNext { logw("Res: $it") }
            onComplete { RxBus.get().post(RxBusTag.VIEWMODEL_CLICK_ADDP_LAYLIST, PlaylistEntity()) }
        }
    }
}