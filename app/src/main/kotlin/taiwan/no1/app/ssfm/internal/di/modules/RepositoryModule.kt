package taiwan.no1.app.ssfm.internal.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Local
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Remote
import taiwan.no1.app.ssfm.models.data.IDataStore
import taiwan.no1.app.ssfm.models.data.remote.RemoteDataStore

/**
 * Dagger module that provides the required objects of [taiwan.no1.app.ssfm.mvvm.models.data.repositories.DataRepository].
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class RepositoryModule {
    @Provides
    @Remote
    fun provideRemoteRepository(context: Context): IDataStore = RemoteDataStore(context)

    @Provides
    @Local
    fun provideLocalRepository(): IDataStore = LocalDataStore()
}