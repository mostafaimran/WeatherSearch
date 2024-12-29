package com.second.source.weathersearch.core

import com.google.gson.annotations.SerializedName
import com.second.source.weathersearch.core.base.BaseApiResponse


open class ApiResponse<T>() : BaseApiResponse() {
    @SerializedName("data")
    var data: T? = null
}