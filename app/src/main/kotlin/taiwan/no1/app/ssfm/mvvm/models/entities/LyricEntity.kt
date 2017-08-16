package taiwan.no1.app.ssfm.mvvm.models.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 * @author  jieyi
 * @since   8/16/17
 */
@Entity(tableName = "lyric_table")
data class LyricEntity constructor(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    @ColumnInfo(name = "lyric")
    var lyric: String)