package taiwan.no1.app.ssfm.mvvm.models.data.remote

import android.content.Context
import dagger.Lazy
import de.umass.lastfm.Album
import de.umass.lastfm.Artist
import de.umass.lastfm.Authenticator
import de.umass.lastfm.Chart
import de.umass.lastfm.Session
import de.umass.lastfm.Track
import de.umass.lastfm.User
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.internal.operators.observable.ObservableJust
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.internal.di.components.NetComponent
import taiwan.no1.app.ssfm.misc.extension.observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.remote.services.MusicServices
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import javax.inject.Inject
import javax.inject.Named

/**
 * Retrieving the data from remote server with [retrofit2] by http api. All return objects are [Observable]
 * to viewmodels.
 *
 * @author  jieyi
 * @since   5/10/17
 */
class RemoteDataStore constructor(private val context: Context): IDataStore {
    @field:[Inject Named("music1")]
    lateinit var musicService1: Lazy<MusicServices>
    @field:[Inject Named("music2")]
    lateinit var musicService2: Lazy<MusicServices>

    private val lastfm_key by lazy { this.context.getString(R.string.lastfm_api_key) }
    private val lastfm_secret by lazy { this.context.getString(R.string.lastfm_secret_key) }

    init {
        NetComponent.Initializer.init().inject(this)
    }

    override fun getSearchMusicRes(keyword: String): Observable<SearchMusicEntity> {
        val query: Map<String, String> = mapOf(
            Pair(this.context.getString(R.string.t_pair1), this.context.getString(R.string.v_pair1)),
            Pair(this.context.getString(R.string.t_pair2), keyword),
            Pair(this.context.getString(R.string.t_pair3), this.context.getString(R.string.v_pair3)),
            Pair(this.context.getString(R.string.t_pair4), this.context.getString(R.string.v_pair4)),
            Pair(this.context.getString(R.string.t_pair5), this.context.getString(R.string.v_pair5)))

        return this.musicService1.get().searchMusic(query).subscribeOn(Schedulers.io())
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity> {
        val query: Map<String, String> = mapOf(
            Pair(this.context.getString(R.string.t_pair6), this.context.getString(R.string.v_pair6)),
            Pair(this.context.getString(R.string.t_pair7), this.context.getString(R.string.v_pair7)),
            Pair(this.context.getString(R.string.t_pair8), hash))

        return this.musicService2.get().getMusic(query).subscribeOn(Schedulers.io())
    }

    override fun obtainSession(user: String, pwd: String): Observable<Session> =
        ObservableJust(Authenticator.getMobileSession(user, pwd, this.lastfm_key, this.lastfm_secret))

    override fun getChartTopArtist(page: Int): Observable<Collection<Artist>> =

        ObservableJust(Chart.getTopArtists(page, this.lastfm_key).pageResults)

    override fun getChartTopTracks(page: Int): Observable<Collection<Track>> =
        ObservableJust(Chart.getTopTracks(page, this.lastfm_key).pageResults)

    override fun getSimilarArtist(artist: String): Observable<Collection<Artist>> =
        ObservableJust(Artist.getSimilar(artist, 10, this.lastfm_key))

    override fun getArtistTopAlbum(artist: String): Observable<Collection<Album>> =
        ObservableJust(Artist.getTopAlbums(artist, this.lastfm_key))

    override fun getArtistTags(artist: String, session: Session): Observable<Collection<String>> =
        ObservableJust(Artist.getTags(artist, session))

    override fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>> =
        ObservableJust(Track.getSimilar(artist, mbid, this.lastfm_key))

    override fun getLovedTracks(user: String, page: Int): Observable<Collection<Track>> =
        ObservableJust(User.getLovedTracks(user, page, this.lastfm_key).pageResults)

    override fun loveTrack(artist: String, track: String, session: Session): Observable<Track> =
        this.observableCreateWrapper {
            val res = Track.love(artist, track, session)

            // TODO: 6/4/17 Add that return next, error, or complete which depend on result's status.
        }

    override fun unloveTrack(artist: String, track: String, session: Session): Observable<Track> =
        this.observableCreateWrapper {
            val res = Track.unlove(artist, track, session)

            // TODO: 6/4/17 Add that return next, error, or complete which depend on result's status.
        }

    /**
     * Wrapping the [Observable.create] with [Schedulers.IO].
     *
     * @param block for processing program.
     * @return an [Observable] reference.
     */
    private fun <O> observableCreateWrapper(block: (emitter: ObservableEmitter<O>) -> Unit): Observable<O> =
        observable { block(it); it.onComplete() }

    /**
     * Wrapping the [Observable.just] with [Schedulers.IO]
     *
     * @param data Omit data.
     * @return an [Observable] reference.
     */
    private fun <O> observableJustWrapper(data: O): Observable<O> = ObservableJust(data)
}