package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import android.view.View
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase

/**
 *
 * @author  jieyi
 * @since   9/20/17
 */
class RecyclerViewArtistChartViewModel(val item: ArtistEntity.Artist,
                                       val usecase: BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>):
    BaseViewModel() {
    val artistName by lazy { ObservableField<String>(item.name) }
    val thumbnail by lazy { ObservableField<String>(item.images?.get(EXTRA_LARGE)?.text ?: "") }

    fun artistOnClick(view: View) {
        lifecycleProvider.execute(usecase,
            GetAlbumInfoCase.RequestValue(item.name ?: "", "")) {
            onNext { logw(it) }
        }
    }
}