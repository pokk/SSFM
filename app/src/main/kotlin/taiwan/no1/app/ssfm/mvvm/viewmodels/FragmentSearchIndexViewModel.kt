package taiwan.no1.app.ssfm.mvvm.viewmodels

import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ChartTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.models.usecases.BaseUsecase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopArtistsCase
import taiwan.no1.app.ssfm.mvvm.models.usecases.GetTopTracksCase


/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchIndexViewModel(private val topArtistsUsecase: BaseUsecase<ChartTopArtistEntity, GetTopArtistsCase.RequestValue>,
                                   private val topTracksUsecase: BaseUsecase<ChartTopTrackEntity, GetTopTracksCase.RequestValue>):
    BaseViewModel() {
    fun fetchArtistList(page: Int = 1, limit: Int = 20, callback: (List<ArtistEntity.Artist>, total: Int) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsCase.RequestValue(page, limit)) {
            onNext { callback(it.artists.artists ?: emptyList(), it.artists.attr?.total?.toInt() ?: 0) }
        }

    fun fetchTrackList(page: Int = 1, limit: Int = 20, callback: (List<TrackEntity.Track>, total: Int) -> Unit) =
        lifecycleProvider.execute(topTracksUsecase, GetTopTracksCase.RequestValue(page, limit)) {
            onNext { callback(it.track.tracks ?: emptyList(), it.track.attr?.total?.toInt() ?: 0) }
        }
}