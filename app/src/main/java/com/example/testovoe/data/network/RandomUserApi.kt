package com.example.testovoe.data.network

import com.example.testovoe.data.models.PersonResponse
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface RandomUserApi {
    @GET("api/")
    suspend fun getAllPersons(
        @QueryMap filters: Map<String, String>
    ): PersonResponse
}