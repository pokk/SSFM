package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Entity
data class Test constructor(@PrimaryKey(autoGenerate = true)
                            @ColumnInfo(name = "id") val id: Int,
                            @ColumnInfo(name = "name") val name: String)