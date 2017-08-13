package taiwan.no1.app.ssfm.mvvm.models.data.remote

import android.content.Context
import dagger.Lazy
import de.umass.lastfm.*
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.internal.di.components.NetComponent
import taiwan.no1.app.ssfm.misc.extension.observable
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.remote.services.MusicServices
import taiwan.no1.app.ssfm.mvvm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.SearchMusicEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

/**
 * Retrieving the data from remote server with [retrofit2] by http api. All return objects are [Observable] to viewmodels.
 *
 * @author  jieyi
 * @since   5/10/17
 */
@Singleton
class RemoteDataStore @Inject constructor(private val context: Context): IDataStore {
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
        this.threadObservableWrapper {
            val session = Authenticator.getMobileSession(user, pwd, this.lastfm_key, this.lastfm_secret)

            it.onNext(session)
        }

    override fun getChartTopArtist(page: Int): Observable<Collection<Artist>> =
        this.threadObservableWrapper {
            val artists = Chart.getTopArtists(page, this.lastfm_key)

            it.onNext(artists.pageResults)
        }

    override fun getChartTopTracks(page: Int): Observable<Collection<Track>> =
        this.threadObservableWrapper {
            val tracks = de.umass.lastfm.Chart.getTopTracks(page, this.lastfm_key)

            it.onNext(tracks.pageResults)
        }

    override fun getSimilarArtist(artist: String): Observable<Collection<Artist>> =
        this.threadObservableWrapper {
            val artists = Artist.getSimilar(artist, 10, this.lastfm_key)

            it.onNext(artists)
        }

    override fun getArtistTopAlbum(artist: String): Observable<Collection<Album>> =
        this.threadObservableWrapper {
            val tracks = Artist.getTopAlbums(artist, this.lastfm_key)

            it.onNext(tracks)
        }

    override fun getArtistTags(artist: String, session: Session): Observable<Collection<String>> =
        this.threadObservableWrapper {
            val tags = de.umass.lastfm.Artist.getTags(artist, session)

            it.onNext(tags)
        }

    override fun getSimilarTracks(artist: String, mbid: String): Observable<Collection<Track>> =
        this.threadObservableWrapper {
            val tracks = Track.getSimilar(artist, mbid, this.lastfm_key)

            it.onNext(tracks)
        }

    override fun getLovedTracks(user: String, page: Int): Observable<Collection<Track>> =
        this.threadObservableWrapper {
            val tracks = User.getLovedTracks(user, page, this.lastfm_key)

            it.onNext(tracks.pageResults)
        }

    override fun loveTrack(artist: String, track: String, session: Session): Observable<Track> =
        this.threadObservableWrapper {
            val res = Track.love(artist, track, session)

            // TODO: 6/4/17 Add that return next, error, or complete which depend on result's status.
        }

    override fun unloveTrack(artist: String, track: String, session: Session): Observable<Track> =
        this.threadObservableWrapper {
            val res = Track.unlove(artist, track, session)

            // TODO: 6/4/17 Add that return next, error, or complete which depend on result's status.
        }

    /**
     * Wrapping the [Observable] with [Schedulers.io].
     *
     * @param
     * @return
     */
    private fun <O> threadObservableWrapper(block: (emitter: ObservableEmitter<O>) -> Unit): Observable<O> =
        observable<O> { block(it); it.onComplete() }.subscribeOn(Schedulers.io())
}