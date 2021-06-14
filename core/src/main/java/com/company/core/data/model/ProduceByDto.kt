package com.company.core.data.model
import android.os.Parcelable
import com.company.core.utility.serializable.ImageSerializer
import com.google.gson.annotations.SerializedName

import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProduceByDto(
    @Expose
    @SerializedName("id")
    var id: Int? = null,
    @Expose
    @JsonAdapter(ImageSerializer::class)
    @SerializedName("logo_path")
    var logoPath: String? = null,
    @Expose
    @SerializedName("name")
    var name: String? = null,
    @Expose
    @SerializedName("origin_country")
    var originCountry: String? = null
): Parcelable