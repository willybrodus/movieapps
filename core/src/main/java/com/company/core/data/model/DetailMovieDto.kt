package com.company.core.data.model
import com.company.core.utility.serializable.DateSerializer
import com.company.core.utility.serializable.ImageSerializer
import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter


data class DetailMovieDto(
    @Expose
    @SerializedName("adult")
    var adult: Boolean? = null,
    @Expose
    @JsonAdapter(ImageSerializer::class)
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @Expose
    @SerializedName("budget")
    var budget: Int? = null,
    @Expose
    @SerializedName("genres")
    var genres: List<Genre>? = null,
    @Expose
    @SerializedName("homepage")
    var homepage: String? = null,
    @Expose
    @SerializedName("id")
    var id: Int? = null,
    @Expose
    @SerializedName("imdb_id")
    var imdbId: String? = null,
    @Expose
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @Expose
    @SerializedName("original_title")
    var originalTitle: String? = null,
    @Expose
    @SerializedName("overview")
    var overview: String? = null,
    @Expose
    @SerializedName("popularity")
    var popularity: Double? = null,
    @Expose
    @JsonAdapter(ImageSerializer::class)
    @SerializedName("poster_path")
    var posterPath: String? = null,
    @Expose
    @SerializedName("production_companies")
    var productionCompanies: List<ProduceByDto>? = null,
    @Expose
    @JsonAdapter(DateSerializer::class)
    @SerializedName("release_date")
    var releaseDate: String? = null,
    @Expose
    @SerializedName("revenue")
    var revenue: Int? = null,
    @Expose
    @SerializedName("runtime")
    var runtime: Int? = null,
    @Expose
    @SerializedName("status")
    var status: String? = null,
    @Expose
    @SerializedName("tagline")
    var tagline: String? = null,
    @Expose
    @SerializedName("title")
    var title: String? = null,
    @Expose
    @SerializedName("video")
    var video: Boolean? = null,
    @Expose
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @Expose
    @SerializedName("vote_count")
    var voteCount: Int? = null,
    var isFavorite : Boolean = false
){
    fun getGenre() : String {
        var genre = ""
        genre = when {
            isGenreMoreThantwo() -> {
                "${genres?.get(0)?.name}, ${genres?.get(1)?.name}, etc"
            }
            genres?.size == 2 -> {
                "${genres?.get(0)?.name}, ${genres?.get(1)?.name}"
            }
            genres?.size == 1 -> {
                "${genres?.get(0)?.name}"
            }
            else -> {
                "Unknow Genre"
            }
        }
        return genre
    }

    private fun isGenreMoreThantwo() : Boolean{
        return genres?.size?:0 > 2
    }
}

