package taiwan.no1.app.ssfm.mvvm.models.data.remote

import android.content.Context
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
    @Inject
    @Named("music1")
    lateinit var musicService: MusicServices

//    @Inject
//    @Named("music2")
//    lateinit var musicService1: MusicServices

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
        val query: Map<String, String> = mapOf(Pair("format", "json"),
                Pair("keyword", keyword),
                Pair("page", "1"),
                Pair("pagesize", "20"),
                Pair("showtype", "1"))

        return this.musicService.searchMusic(query).subscribeOn(Schedulers.io())
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        val query: Map<String, String> = mapOf(Pair("r", "play/getdata"),
                Pair("keyword", hash))
        
        return this.musicService.getMusic(query).subscribeOn(Schedulers.io())
    }
}