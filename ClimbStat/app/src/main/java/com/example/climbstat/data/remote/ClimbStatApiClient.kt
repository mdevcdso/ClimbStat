package com.example.climbstat.data.remote

import com.example.climbstat.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ClimbStatApiClient {
    private val BASE_URL = Constants.BASE_URL
    private val TIMEOUT = 30L

    val httpClient = OkHttpClient.Builder()
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    val authApiService: ClimbStatApiAuthService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClimbStatApiAuthService::class.java)
    }

    val gymApiService: ClimbStatApiGymService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ClimbStatApiGymService::class.java)
    }

}