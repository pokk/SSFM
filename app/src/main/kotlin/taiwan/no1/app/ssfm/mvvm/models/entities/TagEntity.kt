package taiwan.no1.app.ssfm.mvvm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import taiwan.no1.app.ssfm.mvvm.models.data.database.MusicDatabase

/**
 *
 * @author  jieyi
 * @since   8/17/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class TagEntity(@PrimaryKey(autoincrement = true) var id: Long = 0,
                     var name: String = "")