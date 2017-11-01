package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartAlbumDetailViewModel(private val albumInfoCase: BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>):
    BaseViewModel() {
    val artistName by lazy { ObservableField<String>() }
    val artistImage by lazy { ObservableField<String>() }
    val artistSummary by lazy { ObservableField<String>() }

    fun fetchDetailInfo(albumName: String, artistName: String, callback: (albumDetailCallback: AlbumEntity) -> Unit) {
        lifecycleProvider.execute(albumInfoCase, GetAlbumInfoCase.RequestValue(artistName, albumName)) {
            onNext {
                callback(it)
            }
        }
    }
}