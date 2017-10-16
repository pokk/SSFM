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

    @GET(Music3Config.API_REQUEST)
    fun getAlbumInfo(@QueryMap queries: Map<String, String>): Observable<AlbumEntity>
}