package com.second.source.weathersearch.datamodel.models

import com.google.gson.annotations.SerializedName
import com.second.source.weathersearch.datamodel.models.Coord

data class Zila(
    @SerializedName("id") var id: Long,
    @SerializedName("name") var name: String,
    @SerializedName("state") var state: String,
    @SerializedName("country") var country: String,
    @SerializedName("coord") var coord: Coord
)
