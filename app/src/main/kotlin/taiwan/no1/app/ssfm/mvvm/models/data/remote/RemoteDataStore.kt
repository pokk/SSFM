package taiwan.no1.app.ssfm.mvvm.models.data.remote

import android.content.Context
import com.devrapid.kotlinknifer.logd
import de.umass.lastfm.Authenticator
import de.umass.lastfm.Session
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.internal.di.components.NetComponent
import taiwan.no1.app.ssfm.mvvm.models.DetailMusicModel
import taiwan.no1.app.ssfm.mvvm.models.SearchMusicModel
import taiwan.no1.app.ssfm.mvvm.models.data.IDateStore
import taiwan.no1.app.ssfm.mvvm.models.data.remote.services.MusicServices
import javax.inject.Inject
import javax.inject.Named

/**
 *
 * @author  jieyi
 * @since   5/10/17
 */
class RemoteDataStore @Inject constructor(private val context: Context): IDateStore {
    @field:[Inject Named("music1")]
    lateinit var musicService1: MusicServices

    @field:[Inject Named("music2")]
    lateinit var musicService2: MusicServices

    init {
        NetComponent.Initializer.init().inject(this@RemoteDataStore)
    }

    override fun obtainSession(user: String, pwd: String, key: String, secret: String): Observable<Session> =
            Observable.create(ObservableOnSubscribe<Session> {
                val session = Authenticator.getMobileSession(user, pwd, key, secret)
                it.onNext(session)
                it.onComplete()
            }).subscribeOn(Schedulers.io())

    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicModel> {
        val query: Map<String, String> = mapOf(
                Pair("format", "json"),
                Pair("keyword", keyword),
                Pair("page", "1"),
                Pair("pagesize", "20"),
                Pair("showtype", "1"))

        return this.musicService1.searchMusic(query).subscribeOn(Schedulers.io())
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        val query: Map<String, String> = mapOf(
                Pair("format", "json"),
                Pair("r", "play/getdata"),
                Pair("hash", hash))

        logd(query)

//        this.musicService2.getMusic(query).enqueue(object: Callback<ResponseBody> {
//            override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
//                loge(p0)
//                loge(p1)
//            }
//
//            override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
//                logw(p1.body().toString())
//                logd(p0.request().toString())
//            }
//        })

        return this.musicService2.getMusic(query).subscribeOn(Schedulers.io())
//        return Observable.just(DetailMusicModel())
    }
}