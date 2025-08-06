package com.example.testovoe.data.network

import com.example.testovoe.data.models.PersonRequest
import com.example.testovoe.data.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import ru.practicum.android.diploma.util.InternetConnectionChecker

class RetrofitNetworkClient(
    private val service: RetrofitClient,
    private val connectionChecker: InternetConnectionChecker
) : NetworkClient {

    override suspend fun getAllPersons(personRequest: PersonRequest): Response {
        return try {
            withContext(Dispatchers.IO) {
                if (connectionChecker.isInternetAvailable()) {
                    val response = service.randomUserApi.getAllPersons(personRequest.toQueryMap())
                    response.resultCode = OK
                    response
                } else {
                    Response().apply {
                        resultCode = NO_INTERNET
                    }
                }
            }
        } catch (e: HttpException) {
            Response().apply {
                resultCode = e.code()
            }
        }
    }

    companion object {
        private const val NO_INTERNET = -1
        private const val OK = 200
    }
}