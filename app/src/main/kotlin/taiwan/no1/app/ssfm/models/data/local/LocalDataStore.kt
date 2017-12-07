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
import taiwan.no1.app.ssfm.misc.constants.Constant.DATABASE_PLAYLIST_HISTORY_ID
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.entities.KeywordEntity
import taiwan.no1.app.ssfm.models.entities.KeywordEntity_Table
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistEntity_Table
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity
import taiwan.no1.app.ssfm.models.entities.PlaylistItemEntity_Table
import taiwan.no1.app.ssfm.models.entities.SearchMusicEntity
import taiwan.no1.app.ssfm.models.entities.v2.RankChartEntity


/**
 * Retrieving the data from local storage. All return objects are [Observable] to viewmodels.
 *
 * @author  jieyi
 * @since   5/10/17
 */
class LocalDataStore : IDataStore {
    //region V1
    override fun getSearchMusicRes(keyword: String,
                                   page: Int,
                                   pageSize: Int): Observable<SearchMusicEntity> =
        TODO("Local database has no session...")

    override fun getDetailMusicRes(hash: String) = TODO("Local database has no session...")
    //endregion

    //region V2
    override fun searchMusic(keyword: String, page: Int, lang: String) = TODO()

    override fun fetchRankMusic(rankType: Int) = TODO()

    override fun fetchHotPlaylist(page: Int) = TODO()

    override fun fetchPlaylistDetail(id: String) = TODO()
    //endregion

    //region Chart
    override fun getChartTopArtist(page: Int, limit: Int) = TODO()

    override fun getChartTopTracks(page: Int, limit: Int) = TODO()

    override fun getChartTopTags(page: Int, limit: Int) = TODO()

    override fun getChartTop(): Observable<List<RankChartEntity>> = (select from RankChartEntity::class).rx().list.toObservable()

    override fun addRankChart(entity: RankChartEntity): Observable<Boolean> = entity.save().toObservable()

    override fun editRankChart(entity: RankChartEntity): Observable<Boolean> = entity.update().toObservable()
    //endregion

    //region Artist
    override fun getArtistInfo(mbid: String, artist: String) = TODO()

    override fun getSimilarArtist(artist: String) = TODO()

    override fun getArtistTopAlbum(artist: String) = TODO()

    override fun getArtistTopTrack(artist: String) = TODO()

    override fun getArtistTags(artist: String, session: Any) = TODO()

    override fun getSimilarTracks(artist: String, track: String) = TODO()
    //endregion

    //region Album
    override fun getAlbumInfo(artist: String, albumOrMbid: String) = TODO()
    //endregion

    //region Tag
    override fun getTagTopAlbums(tag: String, page: Int, limit: Int) = TODO()

    override fun getTagTopArtists(tag: String, page: Int, limit: Int) = TODO()

    override fun getTagTopTracks(tag: String, page: Int, limit: Int) = TODO()

    override fun getTagInfo(tag: String) = TODO()
    //endregion

    //region Playlist
    override fun getPlaylists(id: Long): Observable<List<PlaylistEntity>> {
        val sqlQuery: BaseModelQueriable<PlaylistEntity> = when (id) {
            -1L -> select from PlaylistEntity::class
            else -> select from PlaylistEntity::class where (PlaylistEntity_Table.id eq id)
        }

        return sqlQuery.rx().list.toObservable()
    }

    override fun addPlaylist(entity: PlaylistEntity) = entity.save().toObservable().flatMap {
        // If adding a new data is success then return the new data; otherwise, an empty object.
        if (it) getPlaylists().flatMap { it.last().toSingle().toObservable() } else PlaylistEntity().toSingle().toObservable()
    }

    override fun editPlaylist(entity: PlaylistEntity) = entity.update().toObservable()

    override fun removePlaylist(entity: PlaylistEntity) = entity.delete().toObservable()

    override fun getPlaylistItems(playlistId: Long) =
        // TODO(jieyi): 12/8/17 Order by click times.
        (select from
            PlaylistItemEntity::class
            where
            (PlaylistItemEntity_Table.playlistId eq playlistId)
            orderBy
            PlaylistItemEntity_Table.clickTimes.asc()).rx().list.toObservable()

    private fun getPlaylistItem(playlistItemId: Long) =
        (select from PlaylistItemEntity::class where (PlaylistItemEntity_Table.id eq playlistItemId)).querySingle()

    override fun addPlaylistItem(entity: PlaylistItemEntity): Observable<Boolean> {
        if (DATABASE_PLAYLIST_HISTORY_ID.toLong() == entity.playlistId) {
            return getPlaylistItem(entity.id)?.let { it.apply { it.clickTimes++ }.save().toObservable() } ?:
                entity.save().toObservable()
        }

        return entity.save().toObservable()
    }

    override fun removePlaylistItem(entity: PlaylistItemEntity) = entity.delete().toObservable()
    //endregion

    //region Search History
    override fun insertKeyword(keyword: String) =
        (select from KeywordEntity::class where (KeywordEntity_Table.keyword eq keyword)).rx().list.
            flatMapObservable {
                (if (0 == it.size) KeywordEntity(keyword = keyword) else it.first().apply { searchTimes += 1 }).save().toObservable()
            }

    override fun getKeywords(quantity: Int) = (quantity.takeIf { 0 < it } ?: Int.MAX_VALUE).let {
        (select from KeywordEntity::class orderBy KeywordEntity_Table.searchTimes.asc() limit it).rx().list.toObservable()
    }

    override fun removeKeywords(keyword: String?) =
        (keyword?.let { (delete(KeywordEntity::class) where (KeywordEntity_Table.keyword eq keyword)).rx() } ?:
            delete(KeywordEntity::class).rx()).
            // The return value of `executeUpdateDelete` is the number of the deleted or updated items.
            executeUpdateDelete().map { 0 < it }.toObservable()
    //endregion
}
