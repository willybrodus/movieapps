package com.company.core.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.company.core.utility.Constant.TABLE_NAME
import com.company.core.utility.DataUtility
import com.company.core.utility.serializable.DateSerializer
import com.company.core.utility.serializable.ImageSerializer
import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import kotlinx.parcelize.Parcelize

@Entity(tableName = TABLE_NAME)
@Parcelize
data class ListMovieDto(
    @Expose
    @SerializedName("genre_ids")
    var genreIds: List<Int>? = null,

    @PrimaryKey(autoGenerate = false)
    @Expose
    @SerializedName("id")
    var id: Int = 0,

    @Expose
    @SerializedName("original_title", alternate = ["original_name"])
    var originalTitle: String? = null,

    @Expose
    @SerializedName("overview")
    var overview: String? = null,

    @Expose
    @JsonAdapter(ImageSerializer::class)
    @SerializedName("poster_path")
    var posterPath: String? = null,

    @Expose
    @JsonAdapter(DateSerializer::class)
    @SerializedName("release_date", alternate = ["first_air_date"])
    var releaseDate: String? = null,

    @Expose
    @SerializedName("title", alternate = ["name"])
    var title: String? = null,

    var isSerial: Boolean = false,

): Parcelable{

    fun getGenre() : String {
        val relational = DataUtility.getDataModelOption()
        var genre = ""
        genre = when {
            isGenreMoreThantwo() -> {
                "${relational[genreIds?.get(0)]}, ${relational[genreIds?.get(1)]}, etc"
            }
            genreIds?.size == 2 -> {
                "${relational[genreIds?.get(0)]}, ${relational[genreIds?.get(1)]}"
            }
            genreIds?.size == 1 -> {
                "${relational[genreIds?.get(0)]}"
            }
            else -> {
                "Unknow Genre"
            }
        }
        return genre
    }

    private fun isGenreMoreThantwo() : Boolean{
        return genreIds?.size?:0 > 2
    }

    override fun toString(): String {
        return "ListMovieDto(genreIds=$genreIds, id=$id, originalTitle=$originalTitle, overview=$overview, posterPath=$posterPath, releaseDate=$releaseDate, title=$title, isSerial=$isSerial)"
    }


}