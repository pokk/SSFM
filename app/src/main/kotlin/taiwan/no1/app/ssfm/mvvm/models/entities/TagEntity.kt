package taiwan.no1.app.ssfm.mvvm.models.entities

import org.greenrobot.greendao.annotation.Entity
import org.greenrobot.greendao.annotation.Id

/**
 *
 * @author  jieyi
 * @since   8/17/17
 */
@Entity
data class TagEntity constructor(@Id(autoincrement = true) var id: Long,
                                 var name: String)