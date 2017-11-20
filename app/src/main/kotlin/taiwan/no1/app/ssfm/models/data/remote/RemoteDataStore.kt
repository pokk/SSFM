package taiwan.no1.app.ssfm.models.data.remote

import android.content.Context
import com.devrapid.kotlinknifer.observable
import dagger.Lazy
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.internal.operators.observable.ObservableJust
import io.reactivex.schedulers.Schedulers
import taiwan.no1.app.ssfm.R
import taiwan.no1.app.ssfm.internal.di.components.NetComponent
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.data.remote.services.MusicServices
import taiwan.no1.app.ssfm.models.data.remote.services.v2.MusicV2Service
import taiwan.no1.app.ssfm.models.entities.DetailMusicEntity
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.AlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistSimilarEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.ArtistTopTrackEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TagTopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopAlbumEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopArtistEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTagEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TopTrackEntity
import taiwan.no1.app.ssfm.models.entities.lastfm.TrackSimilarEntity
import taiwan.no1.app.ssfm.models.entities.v2.HotPlaylistEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.MusicRankEntity
import taiwan.no1.app.ssfm.models.entities.v2.SongListEntity
import javax.inject.Inject
import javax.inject.Named

/**
 * Retrieving the data from remote server with [retrofit2] by http api. All return objects are [Observable]
 * to viewmodels.
 *
 * @author  jieyi
 * @since   5/10/17
 */
class RemoteDataStore constructor(private val context: Context) : IDataStore {
    @field:[Inject Named("music1")]
    lateinit var musicService1: Lazy<MusicServices>
    @field:[Inject Named("music2")]
    lateinit var musicService2: Lazy<MusicServices>
    @field:[Inject Named("music3")]
    lateinit var musicService3: Lazy<MusicServices>
    @field:[Inject Named("music4")]
    lateinit var musicV2Service: Lazy<MusicV2Service>

    private val lastFmKey by lazy { context.getString(R.string.lastfm_api_key) }

    init {
        NetComponent.Initializer.init().inject(this)
    }

    //region V1
    override fun getSearchMusicRes(keyword: String,
                                   page: Int,
                                   pageSize: Int): Observable<SearchMusicEntity> {
        val query = mapOf(
            context.getString(R.string.t_pair1) to context.getString(R.string.v_pair1),
            context.getString(R.string.t_pair2) to keyword,
            context.getString(R.string.t_pair3) to page.toString(),
            context.getString(R.string.t_pair4) to pageSize.toString(),
            context.getString(R.string.t_pair5) to context.getString(R.string.v_pair5))

        return musicService1.get().searchMusic(query)
    }

    override fun getDetailMusicRes(hash: String): Observable<DetailMusicEntity> {
        val query = mapOf(
            context.getString(R.string.t_pair6) to context.getString(R.string.v_pair6),
            context.getString(R.string.t_pair7) to context.getString(R.string.v_pair7),
            context.getString(R.string.t_pair8) to hash)

        return musicService2.get().getMusic(query)
    }
    //endregion

    //region V2
    override fun searchMusic(keyword: String, page: Int, lang: String): Observable<MusicEntity> {
        val query =
            mapOf(context.getString(R.string.s_pair1) to page.toString(),
                context.getString(R.string.s_pair2) to keyword,
                context.getString(R.string.s_pair3) to context.getString(R.string.s_pair4),
                context.getString(R.string.s_pair5) to context.getString(R.string.s_pair6),
                context.getString(R.string.s_pair7) to "en")

        return musicV2Service.get().searchMusic(query)
    }

    override fun fetchRankMusic(rankType: Int): Observable<MusicRankEntity> {
        val query = mapOf(context.getString(R.string.r_pair1) to rankType.toString())

        return musicV2Service.get().musicRanking(query)
    }

    override fun fetchHotPlaylist(page: Int): Observable<HotPlaylistEntity> {
        val query = mapOf(context.getString(R.string.s_pair1) to page.toString())

        return musicV2Service.get().hotPlaylist(query)
    }

    override fun fetchPlaylistDetail(id: String): Observable<SongListEntity> {
        val query = mapOf(context.getString(R.string.b_pair1) to id)

        return musicV2Service.get().playlistDetail(query)
    }
    //endregion

    //region Chart
    override fun getChartTopArtist(page: Int, limit: Int): Observable<TopArtistEntity> {
        val query =
            mutableMapOf("page" to page.toString(),
                "limit" to limit.toString(),
                "method" to "chart.getTopArtists").baseLastFmParams()

        return musicService3.get().getChartTopArtist(query)
    }

    override fun getChartTopTracks(page: Int, limit: Int): Observable<TopTrackEntity> {
        val query =
            mutableMapOf("page" to page.toString(),
                "limit" to limit.toString(),
                "method" to "chart.getTopTracks").baseLastFmParams()

        return musicService3.get().getChartTopTrack(query)
    }

    override fun getChartTopTags(page: Int, limit: Int): Observable<TopTagEntity> {
        val query =
            mutableMapOf("page" to page.toString(),
                "limit" to limit.toString(),
                "method" to "chart.getTopTags").baseLastFmParams()

        return musicService3.get().getChartTopTag(query)
    }
    //endregion

    //region Artist
    override fun getArtistInfo(mbid: String, artist: String): Observable<ArtistEntity> {
        val query =
            mutableMapOf(mbid.takeIf { it.isNotBlank() }?.let { "mbid" to it } ?: "artist" to artist,
                "method" to "artist.getInfo").baseLastFmParams()

        return musicService3.get().getArtistInfo(query)
    }

    override fun getSimilarArtist(artist: String): Observable<ArtistSimilarEntity> {
        val query =
            mutableMapOf("artist" to artist,
                "limit" to 10.toString(),
                "method" to "artist.getSimilar").baseLastFmParams()

        return musicService3.get().getSimilarArtistInfo(query)
    }

    override fun getArtistTopAlbum(artist: String): Observable<ArtistTopAlbumEntity> {
        val query =
            mutableMapOf("artist" to artist,
                "method" to "artist.getTopAlbums").baseLastFmParams()

        return musicService3.get().getArtistTopAlbum(query)
    }

    override fun getArtistTopTrack(artist: String): Observable<ArtistTopTrackEntity> {
        val query =
            mutableMapOf("artist" to artist,
                "method" to "artist.getTopTracks").baseLastFmParams()

        return musicService3.get().getArtistTopTrack(query)
    }

    override fun getArtistTags(artist: String,
                               session: Any): Observable<Collection<String>> = TODO()
//        ObservableJust(Artist.getTags(artist, session))

    override fun getSimilarTracks(artist: String, track: String): Observable<TrackSimilarEntity> {
        val query =
            mutableMapOf("artist" to artist,
                "track" to track,
                "limit" to 10.toString(),
                "method" to "track.getSimilar").baseLastFmParams()

        return musicService3.get().getSimilarTrackInfo(query)
    }
    //endregion

    //region Album
    override fun getAlbumInfo(artist: String, albumOrMbid: String): Observable<AlbumEntity> {
        val query =
            mutableMapOf("method" to "album.getInfo").baseLastFmParams().apply {
                if (artist.isBlank())
                    put("mbid", albumOrMbid)
                else
                    putAll(mapOf("artist" to artist, "album" to albumOrMbid))
            }

        return musicService3.get().getAlbumInfo(query)
    }
    //endregion

    //region Tag
    override fun getTagTopAlbums(tag: String, page: Int, limit: Int): Observable<TopAlbumEntity> {
        val query =
            mutableMapOf("tag" to tag,
                "page" to page.toString(),
                "limit" to limit.toString(),
                "method" to "tag.getTopAlbums").baseLastFmParams()

        return musicService3.get().getTagTopAlbum(query)
    }

    override fun getTagTopArtists(tag: String,
                                  page: Int,
                                  limit: Int): Observable<TagTopArtistEntity> {
        val query =
            mutableMapOf("tag" to tag,
                "page" to page.toString(),
                "limit" to limit.toString(),
                "method" to "tag.getTopArtists").baseLastFmParams()

        return musicService3.get().getTagTopArtist(query)
    }

    override fun getTagTopTracks(tag: String, page: Int, limit: Int): Observable<TopTrackEntity> {
        val query =
            mutableMapOf("tag" to tag,
                "page" to page.toString(),
                "limit" to limit.toString(),
                "method" to "tag.getTopTracks").baseLastFmParams()

        return musicService3.get().getTagTopTrack(query)
    }

    override fun getTagInfo(tag: String): Observable<TagEntity> {
        val query =
            mutableMapOf("tag" to tag,
                "method" to "tag.getInfo").baseLastFmParams()

        return musicService3.get().getTagInfo(query)
    }
    //endregion

    //region Playlist
    override fun getPlaylists(id: Long): Observable<List<PlaylistEntity>> = TODO()

    override fun addPlaylist(entity: PlaylistEntity): Observable<PlaylistEntity> = TODO()

    override fun editPlaylist(entity: PlaylistEntity): Observable<Boolean> = TODO()

    override fun removePlaylist(entity: PlaylistEntity): Observable<Boolean> = TODO()

    override fun getPlaylistItems(playlistId: Long): Observable<List<PlaylistItemEntity>> = TODO()

    override fun addPlaylistItem(entity: PlaylistItemEntity): Observable<Boolean> = TODO()

    override fun removePlaylistItem(entity: PlaylistItemEntity): Observable<Boolean> = TODO()
    //endregion

    //region Search History
    override fun insertKeyword(keyword: String): Observable<Boolean> = TODO()

    override fun getKeywords(quantity: Int): Observable<List<KeywordEntity>> = TODO()

    override fun removeKeywords(keyword: String?): Observable<Boolean> = TODO()
    //endregion

    //region private
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

    private fun MutableMap<String, String>.baseLastFmParams() =
        apply { putAll(mapOf("api_key" to lastFmKey, "format" to "json")) }
    //endregion
}