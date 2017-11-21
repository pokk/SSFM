package taiwan.no1.app.ssfm.functions.chart

import android.databinding.ObservableField
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.models.usecases.FetchTagInfoCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopAlbumOfTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistOfTagCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackOfTagCase
import taiwan.no1.app.ssfm.models.usecases.GetTagInfoUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopAlbumsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopArtistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTagTopTracksUsecase

/**
 * @author  jieyi
 * @since   10/26/17
 */
class ChartTagDetailFragmentViewModel(
    private val tagInfoUsecase: FetchTagInfoCase,
    private val topAlbumsUsecase: FetchTopAlbumOfTagCase,
    private val topArtistsUsecase: FetchTopArtistOfTagCase,
    private val topTracksUsecase: FetchTopTrackOfTagCase) :
    BaseViewModel() {
    val tagSummary by lazy { ObservableField<String>() }

    fun fetchTagDetailInfo(tag: String) {
        lifecycleProvider.execute(tagInfoUsecase, GetTagInfoUsecase.RequestValue(tag)) {
            onNext { tagSummary.set(it.tag.wiki?.summary) }
        }
    }

    fun fetchHotAlbum(name: String, page: Int, limit: Int,
                      callback: (List<BaseEntity>, total: Int) -> Unit) {
        lifecycleProvider.execute(topAlbumsUsecase,
            GetTagTopAlbumsUsecase.RequestValue(name, page, limit)) {
            onNext { callback(it.albums.albums, it.albums.attr?.total?.toInt() ?: 0) }
        }
    }

    fun fetchHotArtist(name: String,
                       page: Int,
                       limit: Int,
                       callback: (List<BaseEntity>, total: Int) -> Unit) {
        lifecycleProvider.execute(topArtistsUsecase,
            GetTagTopArtistsUsecase.RequestValue(name, page, limit)) {
            onNext { callback(it.topartists.artists, it.topartists.attr?.total?.toInt() ?: 0) }
        }
    }

    fun fetchHotTrack(name: String,
                      page: Int,
                      limit: Int,
                      callback: (List<BaseEntity>, total: Int) -> Unit) {
        lifecycleProvider.execute(topTracksUsecase,
            GetTagTopTracksUsecase.RequestValue(name, page, limit)) {
            onNext { callback(it.track.tracks, it.track.attr?.total?.toInt() ?: 0) }
        }
    }
}