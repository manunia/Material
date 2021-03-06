package com.example.material.ui.picture

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class PODRetrofitImpl {
    private val baseUrl = "https://api.nasa.gov/"

    fun getRetrofitImpl() : PictureOfTheDayApi {
        val podRetrofitImpl = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(createOkHttpClient(PODInterceptor()))
            .build()

        return podRetrofitImpl.create(PictureOfTheDayApi::class.java)
    }

    private fun createOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor(interceptor)

        httpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))

        return httpClient.build()
    }

    inner class PODInterceptor: Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {

            return chain.proceed(chain.request())
        }
    }
}