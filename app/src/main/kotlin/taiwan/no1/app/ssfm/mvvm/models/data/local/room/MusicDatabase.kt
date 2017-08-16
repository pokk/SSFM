package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import taiwan.no1.app.ssfm.mvvm.models.entities.LyricEntity

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Database(entities = arrayOf(/*DataBean::class,*/ LyricEntity::class), version = 1)
abstract class MusicDatabase: RoomDatabase() {
    //    abstract fun MusicDao(): MusicDao
    abstract fun lyricDao(): LyricDao
}