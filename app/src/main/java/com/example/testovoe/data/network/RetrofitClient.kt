package com.example.testovoe.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val client by lazy {
        Retrofit.Builder()
            .baseUrl("https://randomuser.me")
            .client(okHttpClient) //
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val randomUserApi: RandomUserApi by lazy {
        client.create(RandomUserApi::class.java)
    }
}