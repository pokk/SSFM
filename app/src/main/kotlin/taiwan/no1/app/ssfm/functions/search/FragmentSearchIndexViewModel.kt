package taiwan.no1.app.ssfm.functions.search

import taiwan.no1.app.ssfm.functions.base.BaseViewModel
import taiwan.no1.app.ssfm.misc.extension.execute


/**
 * @author  jieyi
 * @since   8/20/17
 */
class FragmentSearchIndexViewModel(private val topArtistsUsecase: BaseUsecase<TopArtistEntity, GetTopArtistsCase.RequestValue>,
                                   private val topTracksUsecase: BaseUsecase<TopTrackEntity, GetTopTracksCase.RequestValue>):
    BaseViewModel() {
    fun fetchArtistList(page: Int = 1, limit: Int = 20, callback: (List<ArtistEntity.Artist>, total: Int) -> Unit) =
        lifecycleProvider.execute(topArtistsUsecase, GetTopArtistsCase.RequestValue(page, limit)) {
            onNext { callback(it.artists.artists ?: emptyList(), it.artists.attr.total.toInt() ?: 0) }
        }

    fun fetchTrackList(page: Int = 1, limit: Int = 20, callback: (List<TrackEntity.Track>, total: Int) -> Unit) =
        lifecycleProvider.execute(topTracksUsecase, GetTopTracksCase.RequestValue(page, limit)) {
            onNext { callback(it.track.tracks ?: emptyList(), it.track.attr.total.toInt() ?: 0) }
        }
}