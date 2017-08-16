package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.*
import taiwan.no1.app.ssfm.mvvm.models.entities.TagEntity

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Dao
interface TagDao {
    @Query("SELECT * FROM tag_table")
    fun allTags(): List<TagEntity>

    @Query("SELECT * FROM tag_table WHERE id = :id")
    fun tag(id: String): TagEntity

    @Insert
    fun insertAll(vararg arrayOfTagEntitys: TagEntity)

    @Update
    fun update(vararg arrayOfTagEntitys: TagEntity)

    @Delete
    fun delete(tagEntity: TagEntity)
}