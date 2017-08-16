package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.*
import taiwan.no1.app.ssfm.mvvm.models.entities.LyricEntity

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Dao
interface LyricDao {
    @Query("SELECT * FROM lyric_table")
    fun allLyric(): List<LyricEntity>

    @Query("SELECT * FROM lyric_table WHERE id = :id")
    fun lyric(id: String): LyricEntity

    @Insert
    fun insertAll(vararg notes: LyricEntity)

    @Update
    fun update(vararg note: LyricEntity)

    @Delete
    fun delete(note: LyricEntity)
}