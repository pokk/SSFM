package taiwan.no1.app.ssfm.features.search

import taiwan.no1.app.ssfm.features.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.models.usecases.FetchTopArtistCase
import taiwan.no1.app.ssfm.models.usecases.FetchTopTrackCase
import taiwan.no1.app.ssfm.models.usecases.GetTopArtistsUsecase
import taiwan.no1.app.ssfm.models.usecases.GetTopTracksUsecase

/**
 * @author  jieyi
 * @since   8/20/17
 */
class SearchIndexFragmentViewModel(private val topArtistsUsecase: FetchTopArtistCase,
                                   private val topTracksUsecase: FetchTopTrackCase) : BaseViewModel() {
    fun fetchArtistList(page: Int = 1, limit: Int = 20, callback: (List<ArtistEntity.Artist>, total: Int) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsUsecase.RequestValue(page, limit)) {
            onNext { callback(it.artists.artists, it.artists.attr?.total?.toInt() ?: 0) }
        }

    fun fetchTrackList(page: Int = 1, limit: Int = 20, callback: (List<TrackEntity.Track>, total: Int) -> Unit) =
        lifecycleProvider.execute(topTracksUsecase, GetTopTracksUsecase.RequestValue(page, limit)) {
            onNext { callback(it.track.tracks, it.track.attr?.total?.toInt() ?: 0) }
        }
}