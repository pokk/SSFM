package taiwan.no1.app.ssfm.mvvm.models.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 * @author  jieyi
 * @since   8/17/17
 */
@Entity(tableName = "tag_table")
data class TagEntity constructor(@PrimaryKey(autoGenerate = true) var id: Int,
                                 var name: String)