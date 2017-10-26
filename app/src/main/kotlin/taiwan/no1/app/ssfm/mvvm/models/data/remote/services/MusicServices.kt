package taiwan.no1.app.ssfm.mvvm.models.data.remote.services

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import taiwan.no1.app.ssfm.mvvm.models.data.remote.config.Music1Config
import taiwan.no1.app.ssfm.mvvm.models.data.remote.config.Music2Config
import taiwan.no1.app.ssfm.mvvm.models.data.remote.config.Music3Config
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistSimilarEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopArtistEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTagEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.TrackSimilarEntity

/**
 * Http api request interface set by using [retrofit2.Retrofit].
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface MusicServices {
    @GET(Music1Config.API_REQUEST)
    fun searchMusic(@QueryMap queries: Map<String, String>): Observable<SearchMusicEntity>

    @GET(Music2Config.API_REQUEST)
    fun getMusic(@QueryMap queries: Map<String, String>): Observable<DetailMusicEntity>

    // Album
    @GET(Music3Config.API_REQUEST)
    fun getAlbumInfo(@QueryMap queries: Map<String, String>): Observable<AlbumEntity>

    // Artist
    @GET(Music3Config.API_REQUEST)
    fun getArtistInfo(@QueryMap queries: Map<String, String>): Observable<ArtistEntity>

    @GET(Music3Config.API_REQUEST)
    fun getArtistTopAlbum(@QueryMap queries: Map<String, String>): Observable<TopAlbumEntity>

    @GET(Music3Config.API_REQUEST)
    fun getArtistTopTrack(@QueryMap queries: Map<String, String>): Observable<ArtistTopTrackEntity>

    @GET(Music3Config.API_REQUEST)
    fun getSimilarArtistInfo(@QueryMap queries: Map<String, String>): Observable<ArtistSimilarEntity>

    // Track
    @GET(Music3Config.API_REQUEST)
    fun getTrackInfo(@QueryMap queries: Map<String, String>): Observable<TrackEntity>

    @GET(Music3Config.API_REQUEST)
    fun getSimilarTrackInfo(@QueryMap queries: Map<String, String>): Observable<TrackSimilarEntity>

    // Chart
    @GET(Music3Config.API_REQUEST)
    fun getChartTopTrack(@QueryMap queries: Map<String, String>): Observable<TopTrackEntity>

    @GET(Music3Config.API_REQUEST)
    fun getChartTopArtist(@QueryMap queries: Map<String, String>): Observable<TopArtistEntity>

    @GET(Music3Config.API_REQUEST)
    fun getChartTopTag(@QueryMap queries: Map<String, String>): Observable<TopTagEntity>

    // Tag
    @GET(Music3Config.API_REQUEST)
    fun getTagTopAlbum(@QueryMap queries: Map<String, String>): Observable<TopAlbumEntity>

    @GET(Music3Config.API_REQUEST)
    fun getTagTopArtist(@QueryMap queries: Map<String, String>): Observable<TopArtistEntity>

    @GET(Music3Config.API_REQUEST)
    fun getTagTopTrack(@QueryMap queries: Map<String, String>): Observable<TopTrackEntity>
}