package com.company.core.di

import com.company.core.BuildConfig
import com.company.core.data.source.remote.Api
import com.company.core.data.source.remote.RequestInterceptor
import com.company.core.data.source.remote.ResponseInterceptor
import com.company.core.utility.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.reactivex.schedulers.Schedulers
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
@InstallIn(SingletonComponent::class)
class NetworkModule {

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
                .setLevel(if (BuildConfig.DEBUG){
                    HttpLoggingInterceptor.Level.BODY
                }else{ HttpLoggingInterceptor.Level.NONE})
        )
        .addInterceptor(requestInterceptor)
        .addInterceptor(responseInterceptor)
        .build()

    @Provides
    @Singleton
    fun retrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun api(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


}