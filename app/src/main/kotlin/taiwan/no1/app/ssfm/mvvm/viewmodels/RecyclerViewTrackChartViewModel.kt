package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase
import kotlin.concurrent.thread

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewTrackChartViewModel(val item: TrackEntity.Track,
                                      val usecase: BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>):
    BaseViewModel() {
    val trackName by lazy { ObservableField<String>(item.name) }
    val artistName by lazy { ObservableField<String>(item.artist?.name ?: "") }
    val thumbnail by lazy { ObservableField<String>(item.images?.get(LARGE)?.text) }

    fun trackOnClick(view: View) {
        thread {
            lifecycleProvider.execute(usecase,
                GetAlbumInfoCase.RequestValue(item.artist?.name ?: "", item.mbid ?: "")) {
                onNext { logw(it) }
            }
        }
    }
}