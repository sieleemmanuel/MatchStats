package com.siele.matchstats.data.api

import com.siele.matchstats.util.Constants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit:Retrofit by lazy{
        val logger = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val client = OkHttpClient.Builder()
            .addInterceptor(logger)
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("x-apisports-key", "bbf295ad8aaf9fc96a6ff29c7b7ed0db")
                    .addHeader("x-rapidapi-host", "v3.football.api-sports.io")
                    .build()
                    chain.proceed(newRequest)
            }.build()

        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}