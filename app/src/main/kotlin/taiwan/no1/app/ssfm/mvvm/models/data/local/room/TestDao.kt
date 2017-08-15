package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import junit.framework.Test

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Dao
interface TestDao {
    @Query("select * from test")
    fun findAll(): List<Test>
}