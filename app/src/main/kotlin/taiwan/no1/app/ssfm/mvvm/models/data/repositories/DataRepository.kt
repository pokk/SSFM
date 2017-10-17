package taiwan.no1.app.ssfm.mvvm.models.data.repositories

import de.umass.lastfm.Session
import io.reactivex.Observable
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Local
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Remote
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistSimilarEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackSimilarEntity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * For retrieving the data from the repository of [local] or [remote].
 *
 * @author  jieyi
 * @since   5/10/17
 */
@Singleton
class DataRepository @Inject constructor(@Local private var local: IDataStore,
                                         @Remote private var remote: IDataStore): IDataStore {
    override fun obtainSession(user: String, pwd: String) = remote.obtainSession(user, pwd)

    override fun getSearchMusicRes(keyword: String, page: Int, pageSize: Int) =
        remote.getSearchMusicRes(keyword, page, pageSize)

    override fun getDetailMusicRes(hash: String) = remote.getDetailMusicRes(hash)

    override fun getChartTopArtist(page: Int) = remote.getChartTopArtist(page)

    override fun getChartTopTracks(page: Int) = remote.getChartTopTracks(page)

    override fun getSimilarArtist(artist: String): Observable<ArtistSimilarEntity> = remote.getSimilarArtist(artist)

    override fun getArtistTopAlbum(artist: String): Observable<ArtistTopAlbumEntity> = remote.getArtistTopAlbum(artist)

    override fun getAlbumInfo(artist: String, albumOrMbid: String) = remote.getAlbumInfo(artist, albumOrMbid)

    override fun getArtistTags(artist: String, session: Session) =
        remote.getArtistTags(artist, session)

    override fun getSimilarTracks(artist: String, track: String): Observable<TrackSimilarEntity> =
        remote.getSimilarTracks(artist, track)

    override fun getLovedTracks(user: String, page: Int) =
        remote.getLovedTracks(user, page)

    override fun loveTrack(artist: String, track: String, session: Session) =
        remote.loveTrack(artist, track, session)

    override fun unloveTrack(artist: String, track: String, session: Session) =
        remote.unloveTrack(artist, track, session)

    override fun insertKeyword(keyword: String) = local.insertKeyword(keyword)

    override fun getKeywords(quantity: Int) = local.getKeywords(quantity)

    override fun removeKeywords(keyword: String?) = local.removeKeywords(keyword)
}