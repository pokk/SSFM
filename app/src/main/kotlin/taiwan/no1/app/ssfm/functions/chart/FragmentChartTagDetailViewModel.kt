package taiwan.no1.app.ssfm.mvvm.viewmodels

import android.databinding.ObservableField
import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TagTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTagInfoCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTagTopAlbumsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTagTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTagTopTracksCase

/**
 * @author  jieyi
 * @since   10/26/17
 */
class FragmentChartTagDetailViewModel(
    private val tagInfoUsecase: BaseUsecase<TagEntity, GetTagInfoCase.RequestValue>,
    private val topAlbumsUsecase: BaseUsecase<TopAlbumEntity, GetTagTopAlbumsCase.RequestValue>,
    private val topArtistsUsecase: BaseUsecase<TagTopArtistEntity, GetTagTopArtistsCase.RequestValue>,
    private val topTracksUsecase: BaseUsecase<TopTrackEntity, GetTagTopTracksCase.RequestValue>):
    BaseViewModel() {
    val tagSummary by lazy { ObservableField<String>() }

    fun fetchTagDetailInfo(tag: String) {
        lifecycleProvider.execute(tagInfoUsecase, GetTagInfoCase.RequestValue(tag)) {
            onNext { tagSummary.set(it.tag.wiki?.summary) }
        }
    }

    fun fetchHotAlbum(name: String, page: Int, limit: Int,
                      callback: (List<BaseEntity>, total: Int) -> Unit) {
        lifecycleProvider.execute(topAlbumsUsecase, GetTagTopAlbumsCase.RequestValue(name, page, limit)) {
            onNext { callback(it.albums.albums, it.albums.attr?.total?.toInt() ?: 0) }
        }
    }

    fun fetchHotArtist(name: String, page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit) {
        lifecycleProvider.execute(topArtistsUsecase, GetTagTopArtistsCase.RequestValue(name, page, limit)) {
            onNext { callback(it.topartists.artists, it.topartists.attr?.total?.toInt() ?: 0) }
        }
    }

    fun fetchHotTrack(name: String, page: Int, limit: Int, callback: (List<BaseEntity>, total: Int) -> Unit) {
        lifecycleProvider.execute(topTracksUsecase, GetTagTopTracksCase.RequestValue(name, page, limit)) {
            onNext { callback(it.track.tracks, it.track.attr?.total?.toInt() ?: 0) }
        }
    }
}