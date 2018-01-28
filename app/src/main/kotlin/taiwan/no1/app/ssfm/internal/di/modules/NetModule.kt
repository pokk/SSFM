package taiwan.no1.app.ssfm.internal.di.modules

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import taiwan.no1.app.ssfm.internal.di.annotations.scopes.Network
import taiwan.no1.app.ssfm.models.data.remote.RestfulApiFactory
import taiwan.no1.app.ssfm.models.data.remote.services.MusicServices
import taiwan.no1.app.ssfm.models.data.remote.services.v2.MusicV2Service
import javax.inject.Named

/**
 * Dagger module that provides [Retrofit] libraries, including [OkHttpClient] and [Gson].
 *
 * @author  Jieyi
 * @since   12/6/16
 */
@Module
class NetModule {
    @Provides
    @Network
    fun provideConverterGson(gson: Gson) = GsonConverterFactory.create(gson)

    @Provides
    @Network
    fun provideRxJavaCallAdapter() = RxJava2CallAdapterFactory.create()

    @Provides
    @Network
    fun provideGson() = with(GsonBuilder()) {
        //        setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        setLenient()
        create()
    }

    @Provides
    @Network
    fun provideOkHttpCache(context: Context) = Cache(context.cacheDir,
                                                     10 * 1024 * 1024 /* 10 MiB */)

    @Provides
    @Network
    fun provideOkHttpClient(cache: Cache) = OkHttpClient
        .Builder()
//        .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }).
        .cache(cache)
        .build()

    @Provides
    @Network
    fun provideBaseRetrofitBuilder(converter: GsonConverterFactory,
                                   callAdapter: RxJava2CallAdapterFactory,
                                   okHttpClient: OkHttpClient) =
        Retrofit.Builder().apply {
            addConverterFactory(converter)
            addCallAdapterFactory(callAdapter)
            client(okHttpClient)
        }

    //region TODO: *** We might be able to change base url dynamically. ***
    @Provides
    @Network
    @Named("music1")
    fun provideRetrofit2_1(baseBuilder: Retrofit.Builder,
                           restfulApiFactory: RestfulApiFactory) =
        with(baseBuilder) {
            baseUrl(restfulApiFactory.createMusic1Config().getApiBaseUrl())
            build()
        }.create(MusicServices::class.java)

    @Provides
    @Network
    @Named("music2")
    fun provideRetrofit2_2(baseBuilder: Retrofit.Builder,
                           restfulApiFactory: RestfulApiFactory) =
        with(baseBuilder) {
            baseUrl(restfulApiFactory.createMusic2Config().getApiBaseUrl())
            build()
        }.create(MusicServices::class.java)

    @Provides
    @Network
    @Named("music3")
    fun provideRetrofit2_3(baseBuilder: Retrofit.Builder,
                           restfulApiFactory: RestfulApiFactory) =
        with(baseBuilder) {
            baseUrl(restfulApiFactory.createMusic3Config().getApiBaseUrl())
            build()
        }.create(MusicServices::class.java)

    @Provides
    @Network
    @Named("music4")
    fun provideRetrofit2_4(baseBuilder: Retrofit.Builder,
                           restfulApiFactory: RestfulApiFactory) =
        with(baseBuilder) {
            baseUrl(restfulApiFactory.createMusic4Config().getApiBaseUrl())
            build()
        }.create(MusicV2Service::class.java)
    //endregion
}
