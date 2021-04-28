package com.example.material.ui.picture

import com.example.material.ui.picture.responceData.PODServerResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface PictureOfTheDayApi {

    @GET("planetary/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String) : Call<PODServerResponseData>

    @GET("planetary/apod")
    fun getEarth(@Query("date") date: LocalDate, @Query("api_key") apiKey: String) : Call<PODServerResponseData>
}