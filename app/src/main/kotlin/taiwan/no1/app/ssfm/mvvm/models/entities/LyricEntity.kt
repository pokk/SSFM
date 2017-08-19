package taiwan.no1.app.ssfm.mvvm.models.entities

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.mvvm.models.data.local.database.MusicDatabase

/**
 *
 * @author  jieyi
 * @since   8/16/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class LyricEntity(@PrimaryKey(autoincrement = true) var id: Long = 0,
                       var lyric: String = ""): BaseRXModel()