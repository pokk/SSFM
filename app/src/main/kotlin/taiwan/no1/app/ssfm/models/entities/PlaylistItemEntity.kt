package taiwan.no1.app.ssfm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.models.data.local.database.MusicDatabase
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity
import java.util.Date

/**
 * @author  jieyi
 * @since   11/7/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class PlaylistItemEntity(@PrimaryKey(autoincrement = true)
                              var id: Long = 0,
                              var playlistId: Long = -1,  // History list id is 'DATABASE_PLAYLIST_HISTORY_ID'.
                              var musicId: Int = -1,
                              var isOffline: Boolean = false,  // If the item is offline, fill up as following below.
                              var trackUri: String = "",
                              var trackName: String = "",
                              var artistName: String = "",
                              var albumName: String = "",
                              var lyricUrl: String = "",
                              var coverUrl: String = "",
                              var duration: Int = 0,
                              var timestamp: Date = Date()) : BaseRXModel(), BaseEntity
