package com.company.core.data.source.remote

import com.company.core.data.model.DetailMovieDto
import com.company.core.data.model.DetailSerialDto
import com.company.core.data.model.ListMovieDto
import com.company.core.data.model.PageableModel
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


@JvmSuppressWildcards
interface Api {

    companion object {
        const val LIST_MOVIE = "3/movie/now_playing/"
        const val LIST_SERIAL = "3/tv/airing_today"
        const val DETAIL_MOVIE ="3/movie/{id}"
        const val DETAIL_SERIAL = "3/tv/{id}"
    }

    @GET(LIST_MOVIE)
    fun movieNowPlaying(@Query("page") page: Int,@Query("api_key") key: String = "25b45d4159a068060cea043b79b941c0", @Query("language") language: String = "en-US"): Single<PageableModel<List<ListMovieDto>>>

    @GET(LIST_SERIAL)
    fun serialNowPlaying(@Query("page") page: Int, @Query("api_key") key: String = "25b45d4159a068060cea043b79b941c0", @Query("language") language: String = "en-US"): Single<PageableModel<List<ListMovieDto>>>

    @GET(DETAIL_MOVIE)
    fun detailMovie(@Path("id") id : Int, @Query("api_key") key: String = "25b45d4159a068060cea043b79b941c0", @Query("language") language: String = "en-US"): Observable<DetailMovieDto>

    @GET(DETAIL_SERIAL)
    fun detailSerial(@Path("id") id : Int, @Query("api_key") key: String = "25b45d4159a068060cea043b79b941c0", @Query("language") language: String = "en-US"): Observable<DetailSerialDto>



}