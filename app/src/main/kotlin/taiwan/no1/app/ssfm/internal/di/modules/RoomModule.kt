package taiwan.no1.app.ssfm.internal.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.LocalData
import taiwan.no1.app.ssfm.mvvm.models.data.local.room.NoteDatabase

/**
 *
 * @author  jieyi
 * @since   8/16/17
 */
@Module
class RoomModule {
    @Provides
    @LocalData
    fun providesXXDatabase(context: Context): NoteDatabase =
        Room.databaseBuilder(context, NoteDatabase::class.java, "NoteDatabase").
            allowMainThreadQueries().
            build()
}