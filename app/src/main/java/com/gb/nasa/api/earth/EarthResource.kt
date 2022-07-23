package com.gb.nasa.api.earth

import com.google.gson.annotations.SerializedName

data class EarthResource(
    @field:SerializedName("dataset") val dataset: String?,
    @field:SerializedName("planet") val planet: String?
    )