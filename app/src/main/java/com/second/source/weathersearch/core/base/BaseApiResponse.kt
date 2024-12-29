package com.second.source.weathersearch.core.base

import com.google.gson.annotations.SerializedName

open class BaseApiResponse(
    @SerializedName("success") var success: Boolean = false,
    @SerializedName("message") var message: String? = null
)
