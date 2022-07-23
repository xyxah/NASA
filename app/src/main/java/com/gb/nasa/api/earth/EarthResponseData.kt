package com.gb.nasa.api.earth

import com.google.gson.annotations.SerializedName

data class EarthResponseData(
    @field:SerializedName("date") val date: String?,
    @field:SerializedName("id") val id: String?,
    @field:SerializedName("resource") val resource: EarthResource?,
    @field:SerializedName("dataset") val dataset: String?,
    @field:SerializedName("service_version") val serviceVersion: String?,
    @field:SerializedName("url") val url: String?
    )