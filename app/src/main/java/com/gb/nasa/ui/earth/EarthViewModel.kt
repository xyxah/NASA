package com.gb.nasa.ui.earth

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gb.nasa.BuildConfig
import com.gb.nasa.api.RetrofitImpl
import com.gb.nasa.api.earth.EarthData
import com.gb.nasa.api.earth.EarthResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.roundToInt
import kotlin.random.Random

class EarthViewModel(
    private val liveDataForViewToObserve: MutableLiveData<EarthData> = MutableLiveData(),
    private val retrofitImpl: RetrofitImpl = RetrofitImpl()
) : ViewModel() {

    fun getData(): LiveData<EarthData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = EarthData.Loading(null)

        val lat = Random.nextDouble(-90.00,90.00).roundToInt() / 100.0
        val lon = Random.nextDouble(-90.00,90.00).roundToInt() / 100.0

        val dim = 0.025F
        val date = "today"
        val cloudScore = false

        val apiKey: String = BuildConfig.NASA_API_KEY
        if (apiKey.isBlank()) {
            EarthData.Error(Throwable("You need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getEarthPicture(lat,lon,dim,date,cloudScore,apiKey).enqueue(object :
                Callback<EarthResponseData> {

                override fun onResponse(
                    call: Call<EarthResponseData>,
                    response: Response<EarthResponseData>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        liveDataForViewToObserve.value = EarthData.Success(response.body()!!)
                    } else {
                        val message = response.message()
                        if (message.isNullOrEmpty()) {
                            liveDataForViewToObserve.value = EarthData.Error(Throwable("Unidentified error"))
                        } else {
                            liveDataForViewToObserve.value = EarthData.Error(Throwable(message))
                        }
                    }
                }

                override fun onFailure(call: Call<EarthResponseData>, t:
                Throwable) {
                    liveDataForViewToObserve.value = EarthData.Error(t)
                }
            })
        }
    }
}