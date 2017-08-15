package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import junit.framework.Test

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Database(entities = arrayOf(Test::class), version = 1, exportSchema = false)
abstract class TestDatabase: RoomDatabase() {
    abstract fun getTestDao(): TestDao
}