package com.gb.nasa.api.earth

sealed class EarthData {
    data class Success(val serverResponseData: EarthResponseData) :
        EarthData()
    data class Error(val error: Throwable) : EarthData()
    data class Loading(val progress: Int?) : EarthData()
}
