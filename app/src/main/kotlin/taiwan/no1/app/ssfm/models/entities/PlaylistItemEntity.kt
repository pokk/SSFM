package taiwan.no1.app.ssfm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import taiwan.no1.app.ssfm.mvvm.models.data.local.database.MusicDatabase

/**
 * @author  jieyi
 * @since   11/7/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class PlaylistItemEntity(@PrimaryKey(autoincrement = true) var id: Long = 0,
                              var playlist_id: Int = -1,  // History list id is 65535.
                              var music_id: Int = -1,
                              var is_offline: Boolean = false,
                              var external_track_uri: String = "",
                              var external_track_name: String = "",
                              var external_artist_name: String = "",
                              var external_album_name: String = "")
