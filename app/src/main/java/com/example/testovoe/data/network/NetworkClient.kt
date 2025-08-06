package com.example.testovoe.data.network

import com.example.testovoe.data.models.PersonRequest
import com.example.testovoe.data.models.Response

interface NetworkClient {
    suspend fun getAllPersons(personRequest: PersonRequest): Response
}
