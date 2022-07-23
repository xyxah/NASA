package com.gb.nasa.api.earth

import com.google.gson.annotations.SerializedName

data class EarthRequestData(
    @field:SerializedName("lat") val lat: Float,
    @field:SerializedName("lon") val lon: Float,
    @field:SerializedName("dim") val dim: Float,
    @field:SerializedName("date") val date: String,
    @field:SerializedName("cloud_score") val cloudScore: Boolean,
    @field:SerializedName("api_key") val apiKey: String
    )