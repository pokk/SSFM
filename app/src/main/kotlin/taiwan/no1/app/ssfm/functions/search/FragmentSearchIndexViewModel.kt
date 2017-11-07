package taiwan.no1.app.ssfm.functions.search

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase


/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchIndexViewModel(private val topArtistsUsecase: BaseUsecase<TopArtistEntity, GetTopArtistsCase.RequestValue>,
                                   private val topTracksUsecase: BaseUsecase<TopTrackEntity, GetTopTracksCase.RequestValue>):
    BaseViewModel() {
    fun fetchArtistList(page: Int = 1, limit: Int = 20, callback: (List<ArtistEntity.Artist>, total: Int) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsCase.RequestValue(page, limit)) {
            onNext { callback(it.artists.artists, it.artists.attr?.total?.toInt() ?: 0) }
        }

    fun fetchTrackList(page: Int = 1, limit: Int = 20, callback: (List<TrackEntity.Track>, total: Int) -> Unit) =
        lifecycleProvider.execute(topTracksUsecase, GetTopTracksCase.RequestValue(page, limit)) {
            onNext { callback(it.track.tracks, it.track.attr?.total?.toInt() ?: 0) }
        }
}