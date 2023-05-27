package com.example.tasksproject.data.network

import androidx.viewbinding.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenWeatherService {

    private const val APP_ID = "6cda5fa22c86128ce59936e3d9c8950d"
    private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

    private val okHttpClient: OkHttpClient by lazy {
        getOkHttpClient()
    }
    private val retrofitInstance: OpenWeatherApiService by lazy {
        createRetrofitInstance()
    }


    @JvmName("getOkHttpClient1")
    private fun getOkHttpClient(): OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val modifiedUrl = chain.request().url.newBuilder()
                    .addQueryParameter("appid", APP_ID)
                    .addQueryParameter("units", "metric")
                    .build()

                val request = chain.request().newBuilder().url(modifiedUrl).build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG) {
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }

        return client.build()
    }

    private fun createRetrofitInstance(): OpenWeatherApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(OpenWeatherApiService::class.java)
    }

    fun getInstance(): OpenWeatherApiService = retrofitInstance
}