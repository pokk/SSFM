package taiwan.no1.app.ssfm.mvvm.models.data.remote

import android.content.Context
import de.umass.lastfm.*
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.internal.di.components.NetComponent
import taiwan.no1.app.ssfm.R
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
            Pair(this.context.getString(R.string.t_pair1), this.context.getString(R.string.v_pair1)),
            Pair(this.context.getString(R.string.t_pair2), keyword),
            Pair(this.context.getString(R.string.t_pair3), this.context.getString(R.string.v_pair3)),
            Pair(this.context.getString(R.string.t_pair4), this.context.getString(R.string.v_pair4)),
            Pair(this.context.getString(R.string.t_pair5), this.context.getString(R.string.v_pair5)))

        return this.musicService1.searchMusic(query).subscribeOn(Schedulers.io())
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicModel> {
        val query: Map<String, String> = mapOf(
            Pair(this.context.getString(R.string.t_pair6), this.context.getString(R.string.v_pair6)),
            Pair(this.context.getString(R.string.t_pair7), this.context.getString(R.string.v_pair7)),
            Pair(this.context.getString(R.string.t_pair8), hash))

        return this.musicService2.getMusic(query).subscribeOn(Schedulers.io())
    }

    override fun getChartTopArtist(): Observable<List<Artist>> {
        TODO()
    }

    override fun getChartTopTracks(): Observable<List<Track>> {
        TODO()
    }

    override fun getSimilarArtist(): Observable<List<Artist>> {
        TODO()
    }

    override fun getArtistTopAlbum(): Observable<List<Track>> {
        TODO()
    }

    override fun getArtistTags(): Observable<List<Tag>> {
        TODO()
    }

    override fun getSimilarTracks(): Observable<List<Track>> {
        TODO()
    }

    override fun getLovedTracks(): Observable<List<Track>> {
        TODO()
    }

    override fun loveTrack(): Observable<Track> {
        TODO()
    }

    override fun unloveTrack(): Observable<Track> {
        TODO()
    }
}