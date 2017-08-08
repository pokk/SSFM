package taiwan.no1.app.ssfm.internal.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Local
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Remote
import taiwan.no1.app.ssfm.mvvm.models.data.IDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.local.LocalDataStore
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RemoteDataStore
import javax.inject.Singleton

/**
 *
 * @author  jieyi
 * @since   8/9/17
 */
@Module
class RepositoryModule {
    @Provides
    @Singleton
    @Remote
    fun provideRemoteRepository(context: Context): IDataStore = RemoteDataStore(context)

    @Provides
    @Singleton
    @Local
    fun provideLocalRepository(): IDataStore = LocalDataStore()

//    @Provides
//    @Singleton
//    fun provideDataRepository(@Local local: LocalDataStore, @Remote remote: RemoteDataStore): DataRepository =
//        DataRepository(local, remote)
}