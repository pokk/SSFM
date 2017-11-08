package taiwan.no1.app.ssfm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.models.data.local.database.MusicDatabase
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   9/28/17
 */
// TODO(jieyi): 9/28/17 Might add the `date`.
@Table(database = MusicDatabase::class, allFields = true)
data class KeywordEntity(@PrimaryKey(autoincrement = true) var id: Long = 0,
                         var keyword: String = "",
                         var searchTimes: Long = 1): BaseRXModel(), BaseEntity