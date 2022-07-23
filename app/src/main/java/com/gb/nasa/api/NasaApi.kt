package com.gb.nasa.api

import com.gb.nasa.api.earth.EarthRequestData
import com.gb.nasa.api.earth.EarthResponseData
import com.gb.nasa.api.pod.PODResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String):
            Call<PODResponseData>

    @GET("planetary/earth/imagery")
    fun getEarthPicture(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("dim") dim: Float,
        @Query("date") date: String,
        @Query("cloud_score") cloudScore: Boolean,
        @Query("api_key") apiKey: String
    ): Call<EarthResponseData>
}