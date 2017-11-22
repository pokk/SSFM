package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableArrayList
import android.databinding.ObservableField
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.constants.ImageSizes.EXTRA_LARGE
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.usecases.FetchAlbumInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchArtistInfoCase
import taiwan.no1.app.ssfm.models.usecases.GetAlbumInfoUsecase
import taiwan.no1.app.ssfm.models.usecases.GetArtistInfoUsecase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class ChartAlbumDetailFragmentViewModel(private val albumInfoCase: FetchAlbumInfoCase,
                                        private val artistInfoCase: FetchArtistInfoCase) :
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
        lifecycleProvider.execute(albumInfoCase,
            GetAlbumInfoUsecase.RequestValue(artistName, albumName)) {
            onNext {
                this@ChartAlbumDetailFragmentViewModel.albumName.set(it.album?.name.orEmpty())
                albumSummary.set(it.album?.wiki?.content.orEmpty())
                albumImage.set(it.album?.images?.get(EXTRA_LARGE)?.text.orEmpty())
                albumTags.addAll(it.album?.tags?.tags?.map { it.name } ?: arrayListOf())
                it.album?.let(callback)
            }
        }

        lifecycleProvider.execute(artistInfoCase, GetArtistInfoUsecase.RequestValue(artistName)) {
            onNext {
                this@ChartAlbumDetailFragmentViewModel.artistName.set(artistName)
                it.artist?.images?.get(EXTRA_LARGE)?.text.let { artistImage.set(it) }
            }
        }
    }
}