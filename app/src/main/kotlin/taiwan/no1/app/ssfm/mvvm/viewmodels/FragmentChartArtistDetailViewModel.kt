package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistTopAlbumsCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentChartArtistDetailViewModel(private val artistsInfoUsecase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>,
                                         private val artistTopAlbumsUsecase: BaseUsecase<ArtistTopAlbumEntity, GetArtistTopAlbumsCase.RequestValue>):
    BaseViewModel() {
    val artistName by lazy { ObservableField<String>("") }
    val artistSummary by lazy { ObservableField<String>("") }
    val artistImage by lazy { ObservableField<String>("") }

    fun fetchDetailInfo(mbid: String, name: String, callback: (artistDeatilInfo: ArtistEntity) -> Unit) {
        lifecycleProvider.execute(artistsInfoUsecase, GetArtistInfoCase.RequestValue(name, mbid)) {
            onNext {
                artistImage.set(it.artist?.images?.get(EXTRA_LARGE)?.text ?: "")
                artistName.set(it.artist?.name ?: "")
                artistSummary.set(it.artist?.bio?.summary ?: "")
                callback(it)
            }
        }
    }

    fun fetchHotAlbum(name: String) {
        lifecycleProvider.execute(artistTopAlbumsUsecase, GetArtistTopAlbumsCase.RequestValue(name)) {
            onNext {
                it.topalbums.albums.forEach { }
            }
        }
    }
}