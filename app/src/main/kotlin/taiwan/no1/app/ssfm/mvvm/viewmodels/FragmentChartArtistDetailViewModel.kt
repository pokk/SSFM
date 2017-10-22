package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import com.devrapid.kotlinknifer.logw
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopAlbumsCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartArtistDetailViewModel(private val artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                                         private val topTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetTopAlbumsCase.RequestValue>):
    BaseViewModel() {
    val artistName by lazy { ObservableField<String>("") }
    val artistSummary by lazy { ObservableField<String>("") }
    val artistImage by lazy { ObservableField<String>("") }

    fun fetchDetailInfo(mbid: String, name: String) {
        lifecycleProvider.execute(artistsInfoUsecase, GetArtistInfoCase.RequestValue(name, mbid)) {
            onNext {
                artistImage.set(it.artist?.images?.get(EXTRA_LARGE)?.text ?: "")
                artistName.set(it.artist?.name ?: "")
                artistSummary.set(it.artist?.bio?.summary ?: "")
            }
        }
    }

    fun fetchHotAlbum(name: String) {
        lifecycleProvider.execute(topTopAlbumsUsecase, GetTopAlbumsCase.RequestValue(name)) {
            onNext {
                it.topalbums.album?.forEach { logw(it.name) }
            }
        }
    }
}