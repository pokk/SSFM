package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Entity(tableName = "note")
data class Note constructor(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                            @ColumnInfo(name = "title") var title: String,
                            @ColumnInfo(name = "contents") var contents: String)