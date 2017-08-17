package taiwan.no1.app.ssfm.mvvm.models.data.local.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import taiwan.no1.app.ssfm.mvvm.models.data.local.room.dao.LyricDao
import taiwan.no1.app.ssfm.mvvm.models.data.local.room.dao.MusicDao
import taiwan.no1.app.ssfm.mvvm.models.data.local.room.dao.TagDao
import taiwan.no1.app.ssfm.mvvm.models.entities.DataBean
import taiwan.no1.app.ssfm.mvvm.models.entities.LyricEntity
import taiwan.no1.app.ssfm.mvvm.models.entities.TagEntity

/**
 *
 * @author  jieyi
 * @since   8/15/17
 */
@Database(entities = arrayOf(DataBean::class, LyricEntity::class, TagEntity::class), version = 1)
abstract class MusicDatabase: RoomDatabase() {
    abstract fun MusicDao(): MusicDao

    abstract fun lyricDao(): LyricDao

    abstract fun tagDao(): TagDao
}