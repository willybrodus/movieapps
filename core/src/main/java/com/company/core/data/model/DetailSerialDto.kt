package com.company.core.data.model
import com.company.core.utility.serializable.DateSerializer
import com.company.core.utility.serializable.ImageSerializer
import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter


data class DetailSerialDto(
    @Expose
    @JsonAdapter(ImageSerializer::class)
    @SerializedName("backdrop_path")
    var backdropPath: String? = null,
    @Expose
    @SerializedName("episode_run_time")
    var episodeRunTime: List<Int>? = null,
    @Expose
    @JsonAdapter(DateSerializer::class)
    @SerializedName("first_air_date")
    var firstAirDate: String? = null,
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
    @SerializedName("in_production")
    var inProduction: Boolean? = null,
    @Expose
    @SerializedName("languages")
    var languages: List<String>? = null,
    @Expose
    @SerializedName("last_air_date")
    @JsonAdapter(DateSerializer::class)
    var lastAirDate: String? = null,
    @Expose
    @SerializedName("last_episode_to_air")
    var lastEpisodeToAir: LastEpisodeToAir? = null,
    @Expose
    @SerializedName("name")
    var name: String? = null,
    @Expose
    @SerializedName("next_episode_to_air")
    var nextEpisodeToAir: NextEpisodeToAir? = null,
    @Expose
    @SerializedName("number_of_episodes")
    var numberOfEpisodes: Int? = null,
    @Expose
    @SerializedName("number_of_seasons")
    var numberOfSeasons: Int? = null,
    @Expose
    @SerializedName("origin_country")
    var originCountry: List<String>? = null,
    @Expose
    @SerializedName("original_language")
    var originalLanguage: String? = null,
    @Expose
    @SerializedName("original_name")
    var originalName: String? = null,
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
    @SerializedName("status")
    var status: String? = null,
    @Expose
    @SerializedName("tagline")
    var tagline: String? = null,
    @Expose
    @SerializedName("type")
    var type: String? = null,
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