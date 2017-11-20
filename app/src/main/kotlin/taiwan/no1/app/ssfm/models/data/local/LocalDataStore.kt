package taiwan.no1.app.ssfm.models.data.local

import com.raizlabs.android.dbflow.kotlinextensions.delete
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.limit
import com.raizlabs.android.dbflow.kotlinextensions.orderBy
import com.raizlabs.android.dbflow.kotlinextensions.select
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.rx2.kotlinextensions.list
import com.raizlabs.android.dbflow.rx2.kotlinextensions.rx
import com.raizlabs.android.dbflow.sql.language.BaseModelQueriable
import io.reactivex.Observable
import io.reactivex.rxkotlin.toSingle
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.KeywordEntity_Table
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity_Table
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity_Table
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity


/**
 * Retrieving the data from local storage. All return objects are [Observable] to viewmodels.
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LocalDataStore : IDataStore {
    override fun getSearchMusicRes(keyword: String,
                                   page: Int,
                                   pageSize: Int): Observable<SearchMusicEntity> =
        TODO("Local database has no session...")

    override fun getDetailMusicRes(hash: String) = TODO("Local database has no session...")

    override fun searchMusic(keyword: String, page: Int, lang: String) = TODO()

    override fun fetchRankMusic(rankType: Int) = TODO()

    override fun fetchHotPlaylist(page: Int) = TODO()

    override fun fetchPlaylistDetail(id: String) = TODO()

    override fun getChartTopArtist(page: Int, limit: Int) = TODO()

    override fun getChartTopTracks(page: Int, limit: Int) = TODO()

    override fun getChartTopTags(page: Int, limit: Int) = TODO()

    override fun getArtistInfo(mbid: String, artist: String) = TODO()

    override fun getSimilarArtist(artist: String) = TODO()

    override fun getArtistTopAlbum(artist: String) = TODO()

    override fun getArtistTopTrack(artist: String) = TODO()

    override fun getAlbumInfo(artist: String, albumOrMbid: String) = TODO()

    override fun getArtistTags(artist: String, session: Any) = TODO()

    override fun getSimilarTracks(artist: String, track: String) = TODO()

    override fun getTagTopAlbums(tag: String, page: Int, limit: Int) = TODO()

    override fun getTagTopArtists(tag: String, page: Int, limit: Int) = TODO()

    override fun getTagTopTracks(tag: String, page: Int, limit: Int) = TODO()

    override fun getTagInfo(tag: String) = TODO()

    override fun getPlaylists(id: Long): Observable<List<PlaylistEntity>> {
        val sqlQuery: BaseModelQueriable<PlaylistEntity> = when (id) {
            -1L -> select from PlaylistEntity::class
            else -> select from PlaylistEntity::class where (PlaylistEntity_Table.id eq id)
        }

        return sqlQuery.rx().list.toObservable()
    }

    override fun addPlaylist(entity: PlaylistEntity): Observable<PlaylistEntity> = entity.save().toObservable().flatMap {
        // If adding a new data is success then return the new data; otherwise, an empty object.
        if (it) getPlaylists().flatMap { it.last().toSingle().toObservable() } else PlaylistEntity().toSingle().toObservable()
    }

    override fun editPlaylist(entity: PlaylistEntity) = entity.update().toObservable()

    override fun removePlaylist(entity: PlaylistEntity) = entity.delete().toObservable()

    override fun getPlaylistItems(playlistId: Long) =
        (select from PlaylistItemEntity::class where (PlaylistItemEntity_Table.playlist_id eq playlistId)).rx().list.toObservable()

    override fun addPlaylistItem(entity: PlaylistItemEntity) = entity.save().toObservable()

    override fun removePlaylistItem(entity: PlaylistItemEntity) = entity.delete().toObservable()

    override fun insertKeyword(keyword: String) =
        (select from KeywordEntity::class where (KeywordEntity_Table.keyword eq keyword)).rx().list.
            flatMapObservable {
                (if (0 == it.size) KeywordEntity(keyword = keyword) else it.first().apply { searchTimes += 1 }).save().toObservable()
            }

    override fun getKeywords(quantity: Int) = (quantity.takeIf { 0 < it } ?: Int.MAX_VALUE).let {
        (select from KeywordEntity::class orderBy KeywordEntity_Table.searchTimes.asc() limit it).rx().list.toObservable()
    }

    override fun removeKeywords(keyword: String?): Observable<Boolean> {
        return (keyword?.let { (delete(KeywordEntity::class) where (KeywordEntity_Table.keyword eq keyword)).rx() } ?:
            delete(KeywordEntity::class).rx()).
            // The return value of `executeUpdateDelete` is the number of the deleted or updated items.
            executeUpdateDelete().map { 0 < it }.toObservable()
    }
}
