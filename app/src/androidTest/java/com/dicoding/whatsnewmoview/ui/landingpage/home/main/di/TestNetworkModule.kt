package com.dicoding.whatsnewmoview.ui.landingpage.home.main.di

import com.company.core.data.source.remote.Api
import com.company.core.data.source.remote.RequestInterceptor
import com.company.core.data.source.remote.ResponseInterceptor
import com.company.core.di.NetworkModule
import com.company.core.utility.Constant
import com.dicoding.whatsnewmoview.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@TestInstallIn( components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class TestNetworkModule {

    private val certificatePinner = CertificatePinner.Builder()
        .add("api.themoviedb.org", "sha256/+vqZVAzTqUP8BGkfl88yU7SQ3C8J2uNEa55B7RZjEg0=")
        .build()

    @Provides
    @Singleton
    fun requestInterceptor(interceptor: RequestInterceptor) : Interceptor = interceptor

    @Provides
    @Singleton
    fun responseInterceptor(interceptor: ResponseInterceptor): Interceptor = interceptor

    @Provides
    @Singleton
    fun okHttp(requestInterceptor: RequestInterceptor, responseInterceptor: ResponseInterceptor) = OkHttpClient().newBuilder()
        .connectTimeout(Constant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(Constant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(Constant.NETWORK_TIMEOUT, TimeUnit.SECONDS)
        .certificatePinner(certificatePinner)
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(if (com.company.core.BuildConfig.DEBUG){
                    HttpLoggingInterceptor.Level.BODY
                }else{ HttpLoggingInterceptor.Level.NONE})
        )
        .addInterceptor(requestInterceptor)
        .addInterceptor(responseInterceptor)
        .build()

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(com.company.core.BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun api(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


    @Provides
    fun giveTestScheduler(): TestScheduler? = null
}