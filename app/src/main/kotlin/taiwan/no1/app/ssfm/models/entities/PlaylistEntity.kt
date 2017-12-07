package taiwan.no1.app.ssfm.models.entities

import android.os.Parcelable
import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import kotlinx.android.parcel.Parcelize
import taiwan.no1.app.ssfm.models.data.local.database.MusicDatabase
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   11/7/17
 */
@Parcelize
@Table(database = MusicDatabase::class, allFields = true)
data class PlaylistEntity(@PrimaryKey(autoincrement = true)
                          var id: Long = 0,
                          var name: String = "",
                          var imageUri: String = "",
                          var trackQuantity: Int = 0,
                          var albumQuantity: Int = 0,
                          var lastPlayedItem: Int = -1,
                          var duration: Int = 0,
                          var isHistory: Boolean = false,
                          var isRandom: Boolean = false) : BaseRXModel(), BaseEntity, Parcelable