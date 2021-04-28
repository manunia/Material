package com.example.material.ui.picture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.material.BuildConfig
import com.example.material.ui.picture.responceData.PODServerResponseData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PictureOfTheDayViewModel(
    private val liveDataForViewToObserve: MutableLiveData<PictureOfTheDayData> = MutableLiveData(),
    private val retrofitImpl: PODRetrofitImpl = PODRetrofitImpl()
) : ViewModel() {
    fun getData(): LiveData<PictureOfTheDayData> {
        sendServerRequest()
        return liveDataForViewToObserve
    }

    private fun sendServerRequest() {
        liveDataForViewToObserve.value = PictureOfTheDayData.Loading(null)

        val apiKey: String = BuildConfig.NASA_API_KEY

        if (apiKey.isBlank()) {
            liveDataForViewToObserve.value =
                PictureOfTheDayData.Error(Throwable("No API KEY! Need API key"))
        } else {
            retrofitImpl.getRetrofitImpl().getPictureOfTheDay(apiKey)
                .enqueue(object : Callback<PODServerResponseData> {
                    override fun onFailure(call: Call<PODServerResponseData>, t: Throwable) {
                        liveDataForViewToObserve.value = PictureOfTheDayData.Error(t)
                    }

                    override fun onResponse(
                        call: Call<PODServerResponseData>,
                        response: Response<PODServerResponseData>
                    ) {
                        if (response.isSuccessful && response.body() != null) {
                            liveDataForViewToObserve.value =
                                PictureOfTheDayData.Success(response.body()!!)
                        } else {
                            val message: String = response.message()

                            if (message.isNullOrEmpty()) {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Error(Throwable("Error!!!!!"))
                            } else {
                                liveDataForViewToObserve.value =
                                    PictureOfTheDayData.Error(Throwable(message))
                            }
                        }
                    }
                })
        }
    }
}
