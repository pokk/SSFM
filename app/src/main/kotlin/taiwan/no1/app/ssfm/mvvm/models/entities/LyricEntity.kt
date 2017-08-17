package taiwan.no1.app.ssfm.mvvm.models.entities

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 *
 * @author  jieyi
 * @since   8/16/17
 */
@Entity
data class LyricEntity constructor(@Id(autoincrement = true) var id: Long,
                                   var lyric: String)