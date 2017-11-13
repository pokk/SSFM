package taiwan.no1.app.ssfm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.models.data.local.database.MusicDatabase
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   11/7/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class PlaylistItemEntity(@PrimaryKey(autoincrement = true) var id: Long = 0,
                              var playlist_id: Int = -1,  // History list id is 65535.
                              var music_id: Int = -1,
                              var is_offline: Boolean = false,
                              var track_uri: String = "",
                              var track_name: String = "",
                              var artist_name: String = "",
                              var album_name: String = "") : BaseRXModel(), BaseEntity
