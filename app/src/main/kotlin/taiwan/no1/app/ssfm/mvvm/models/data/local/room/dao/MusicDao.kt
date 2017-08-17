package taiwan.no1.app.ssfm.mvvm.models.data.local.room.dao

import android.arch.persistence.room.*
import taiwan.no1.app.ssfm.mvvm.models.entities.DataBean

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Dao
interface MusicDao {
    @Query("SELECT * FROM music_table")
    fun allMusic(): List<DataBean>

    @Query("SELECT * FROM music_table WHERE id = :id")
    fun music(id: String): DataBean

    @Insert
    fun insertAll(vararg notes: DataBean)

    @Update
    fun update(vararg note: DataBean)

    @Delete
    fun delete(note: DataBean)
}