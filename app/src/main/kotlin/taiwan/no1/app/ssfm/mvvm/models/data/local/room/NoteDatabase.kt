package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Database(entities = arrayOf(Note::class), version = 1)
abstract class NoteDatabase: RoomDatabase() {
    abstract fun noteDao(): NoteDao
}