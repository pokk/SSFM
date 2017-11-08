package taiwan.no1.app.ssfm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.mvvm.models.data.local.database.MusicDatabase
import taiwan.no1.app.ssfm.mvvm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   11/7/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class PlaylistEntity(@PrimaryKey(autoincrement = true) var id: Long = 0,
                          var name: String = "",
                          var image_uri: String = "",
                          var track_quantity: Int = -1,
                          var album_quantity: Int = -1,
                          var last_played_item: Int = -1,
                          var is_random: Boolean = false): BaseRXModel(), BaseEntity