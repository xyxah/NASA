package com.gb.nasa.ui.pod

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.nasa.BuildConfig
import com.gb.nasa.api.RetrofitImpl
import com.gb.nasa.api.pod.PODResponseData
import com.gb.nasa.api.pod.PODData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PODData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) :
    ViewModel() {
    fun getData(): LiveData<PODData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }
    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PODData.Loading(null)
        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            PODData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey).enqueue(object :
                Callback<PODResponseData> {
                override fun onResponse(
                    call: Call<PODResponseData>,
                    response: Response<PODResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = PODData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value = PODData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value = PODData.Error(Throwable(message))
                        }
                    }
                }
                override fun onFailure(call: Call<PODResponseData>, t:
                Throwable) {
                    liveDataForViewToObserve.value = PODData.Error(t)
                }
            })
        }
    }
}
