package com.company.core.data.model

import com.company.core.utility.serializable.DateSerializer
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName

data class LastEpisodeToAir(
    @Expose
    @SerializedName("air_date")
    @JsonAdapter(DateSerializer::class)
    var airDate: String? = null,
    @Expose
    @SerializedName("episode_number")
    var episodeNumber: Int? = null,
    @Expose
    @SerializedName("id")
    var id: Int? = null,
    @Expose
    @SerializedName("name")
    var name: String? = null,
    @Expose
    @SerializedName("overview")
    var overview: String? = null,
    @Expose
    @SerializedName("production_code")
    var productionCode: String? = null,
    @Expose
    @SerializedName("season_number")
    var seasonNumber: Int? = null,
    @Expose
    @SerializedName("still_path")
    var stillPath: String? = null,
    @Expose
    @SerializedName("vote_average")
    var voteAverage: Double? = null,
    @Expose
    @SerializedName("vote_count")
    var voteCount: Int? = null
)