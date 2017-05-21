package taiwan.no1.app.ssfm.mvvm.models.data.remote.services

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap
import taiwan.no1.app.ssfm.mvvm.models.DetailMusicModel
import taiwan.no1.app.ssfm.mvvm.models.SearchMusicModel

/**
 * Http api request interface set by using [retrofit2.Retrofit].
 *
 * @author  jieyi
 * @since   5/10/17
 */
interface MusicServices {
    @GET("song")
    fun searchMusic(@QueryMap queries: Map<String, String>): Observable<SearchMusicModel>

    @GET("")
    fun getMusic(@QueryMap queries: Map<String, String>): Observable<DetailMusicModel>
}