package com.gb.nasa.api.pod

sealed class PODData {
    data class Success(val serverResponseData: PODResponseData) :
        PODData()
    data class Error(val error: Throwable) : PODData()
    data class Loading(val progress: Int?) : PODData()
}
