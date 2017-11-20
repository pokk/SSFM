package taiwan.no1.app.ssfm.models.data.remote.services.v2

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import taiwan.no1.app.ssfm.models.data.remote.config.Music4Config
import taiwan.no1.app.ssfm.models.entities.v2.HotPlaylistEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.entities.v2.SongListEntity

/**
 * @author  jieyi
 * @since   11/20/17
 */
interface MusicV2Service {
    @GET("${Music4Config.API_REQUEST}/rank_song_list")
    fun musicRanking(@QueryMap queries: Map<String, String>): Observable<MusicRankEntity>

    @GET("${Music4Config.API_REQUEST}/search")
    fun searchMusic(@QueryMap queries: Map<String, String>): Observable<MusicEntity>

    @GET("${Music4Config.API_REQUEST}/hot_song_list")
    fun hotPlaylist(@QueryMap queries: Map<String, String>): Observable<HotPlaylistEntity>

    @GET("${Music4Config.API_REQUEST}/song_list")
    fun playlistDetail(@QueryMap queries: Map<String, String>): Observable<SongListEntity>
}