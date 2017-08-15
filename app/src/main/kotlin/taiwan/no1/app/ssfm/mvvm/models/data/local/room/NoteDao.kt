package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*


/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Dao
interface NoteDao {
    @Query("SELECT * FROM note")
    fun allNotes(): LiveData<List<Note>>

    @Query("SELECT * FROM note WHERE id = :id")
    fun note(id: String): LiveData<Note>

    @Insert
    fun insertAll(vararg notes: Note)

    @Update
    fun update(vararg note: Note)

    @Delete
    fun delete(note: Note)
}