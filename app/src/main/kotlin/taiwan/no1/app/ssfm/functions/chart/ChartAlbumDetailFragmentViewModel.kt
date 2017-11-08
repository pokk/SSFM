package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetAlbumInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetArtistInfoCase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartAlbumDetailFragmentViewModel(private val albumInfoCase: BaseUsecase<AlbumEntity, GetAlbumInfoCase.RequestValue>,
                                        private val artistInfoCase: BaseUsecase<ArtistEntity, GetArtistInfoCase.RequestValue>):
    BaseViewModel() {
    val artistImage by lazy { ObservableField<String>() }
    val artistName by lazy { ObservableField<String>() }
    val albumName by lazy { ObservableField<String>() }
    val albumSummary by lazy { ObservableField<String>() }
    val albumImage by lazy { ObservableField<String>() }
    val albumTags by lazy { ObservableArrayList<String>() }

    fun fetchDetailInfo(albumName: String,
                        artistName: String,
                        callback: (albumDetailCallback: AlbumEntity.Album) -> Unit) {
        lifecycleProvider.execute(albumInfoCase, GetAlbumInfoCase.RequestValue(artistName, albumName)) {
            onNext {
                this@ChartAlbumDetailFragmentViewModel.albumName.set(it.album?.name ?: "")
                albumSummary.set(it.album?.wiki?.content ?: "")
                albumImage.set(it.album?.images?.get(EXTRA_LARGE)?.text ?: "")
                albumTags.addAll(it.album?.tags?.tags?.map { it.name } ?: arrayListOf())
                it.album?.let(callback)
            }
        }

        lifecycleProvider.execute(artistInfoCase, GetArtistInfoCase.RequestValue(artistName)) {
            onNext {
                this@ChartAlbumDetailFragmentViewModel.artistName.set(artistName)
                it.artist?.images?.get(EXTRA_LARGE)?.text.let { artistImage.set(it) }
            }
        }
    }
}