package taiwan.no1.app.ssfm.internal.di.modules

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Music1
import taiwan.no1.app.ssfm.internal.di.annotations.qualifiers.Music2
import taiwan.no1.app.ssfm.mvvm.models.data.remote.RestfulApiFactory
import taiwan.no1.app.ssfm.mvvm.models.data.remote.services.MusicServices
import javax.inject.Singleton

/**
 *
 * @author  Jieyi
 * @since   12/6/16
 */

@Module
class NetModule(val context: Context) {
    @Provides
    @Singleton
    fun provideConverterGson(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

    @Provides
    @Singleton
    fun provideRxJavaCallAdapter(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory.create()

    @Provides
    @Singleton
    fun provideGson(): Gson = with(GsonBuilder()) {
        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        setLenient()
        create()
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(): Cache = Cache(context.cacheDir, 10 * 1024 * 1024 /* 10 MiB */)

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder().cache(cache).build()

    @Provides
    @Singleton
    fun provideBaseRetrofitBuilder(converter: GsonConverterFactory,
                                   callAdapter: RxJava2CallAdapterFactory,
                                   okHttpClient: OkHttpClient): Retrofit.Builder =
        Retrofit.Builder().apply {
            addConverterFactory(converter)
            addCallAdapterFactory(callAdapter)
            client(okHttpClient)
        }

    @Provides
    @Singleton
    @Music1
    fun provideRetrofit2_1(baseBuilder: Retrofit.Builder, restfulApiFactory: RestfulApiFactory): MusicServices =
        with(baseBuilder) {
            baseUrl(restfulApiFactory.createMusic1Config().getApiBaseUrl())
            build()
        }.create(MusicServices::class.java)

    @Provides
    @Singleton
    @Music2
    fun provideRetrofit2_2(baseBuilder: Retrofit.Builder, restfulApiFactory: RestfulApiFactory): MusicServices =
        with(baseBuilder) {
            baseUrl(restfulApiFactory.createMusic2Config().getApiBaseUrl())
            build()
        }.create(MusicServices::class.java)
}
