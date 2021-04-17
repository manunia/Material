package com.example.material.ui.picture

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface PictureOfTheDayApi {

    @GET("planetaty/apod")
    fun getPictureOfTheDay(@Query("api_key") apiKey: String) : Call<PODServerResponseData>
}