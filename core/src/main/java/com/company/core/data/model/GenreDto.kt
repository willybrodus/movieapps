package com.company.core.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Genre(
    @Expose
    @SerializedName("id")
    var id: Int? = null,
    @Expose
    @SerializedName("name")
    var name: String? = null
)