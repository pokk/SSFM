package taiwan.no1.app.ssfm.models.entities.v2

import com.raizlabs.android.dbflow.annotation.PrimaryKey
import com.raizlabs.android.dbflow.annotation.Table
import com.raizlabs.android.dbflow.rx2.structure.BaseRXModel
import taiwan.no1.app.ssfm.models.data.local.database.MusicDatabase
import taiwan.no1.app.ssfm.models.entities.lastfm.BaseEntity

/**
 * @author  jieyi
 * @since   11/24/17
 */
@Table(database = MusicDatabase::class, allFields = true)
data class RankChartEntity(@PrimaryKey(autoincrement = true)
                           var id: Long = 0,
                           var rankType: Int = 0,
                           var coverUrl: String = "",
                           var chartName: String = "",
                           var updateTime: String = "") : BaseRXModel(), BaseEntity