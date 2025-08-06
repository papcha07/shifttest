package com.example.testovoe.data

import com.example.testovoe.data.models.PersonRequest
import com.example.testovoe.data.models.PersonResponse
import com.example.testovoe.data.network.NetworkClient
import com.example.testovoe.data.network.PersonDao
import com.example.testovoe.data.network.toEntity
import com.example.testovoe.data.network.toPreview
import com.example.testovoe.domain.api.PersonRepository
import com.example.testovoe.domain.models.FailureType
import com.example.testovoe.domain.models.PersonPreview
import com.example.testovoe.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException

class PersonRepositoryImpl(
    private val networkClient: NetworkClient,
    private val personDao: PersonDao
) : PersonRepository {

    private fun getNetworkRequest() = flow {
        try {
            val response = networkClient.getAllPersons(
                PersonRequest(results = 10)
            )
            when (response.resultCode) {
                OK_RESPONSE -> {
                    val personPreviewList = (response as PersonResponse).results.map { result ->
                        PersonPreview(
                            id = result.login.uuid,
                            first = result.name.first,
                            last = result.name.last,
                            streetNumber = result.location.street.number.toString(),
                            streetName = result.location.street.name,
                            thumbnail = result.picture.large,
                            phone = result.phone,
                            country = result.location.country,
                            city = result.location.city,
                            email = result.email,
                        )
                    }
                    personDao.clearAllPersons()
                    personDao.insertPersons(personPreviewList.map { it.toEntity() })

                    val newCachedData = personDao.getAllPersons().map { it.toPreview() }
                    emit(Resource.Success(newCachedData))
                }

                NO_INTERNET -> emit(Resource.Failed(FailureType.NoInternet))
                else -> emit(Resource.Failed(FailureType.ApiError))
            }
        } catch (e: IOException) {
            emit(Resource.Failed(FailureType.NoInternet))
        }
    }

    override fun getAllPersons(): Flow<Resource<List<PersonPreview>>> = flow {
        val cachedPersons = personDao.getAllPersons().map { it.toPreview() }
        if (cachedPersons.isNotEmpty()) {
            emit(Resource.Success(cachedPersons))
        } else {
            emitAll(getNetworkRequest())
        }
    }

    override fun getPersonsFromInternet(): Flow<Resource<List<PersonPreview>>> = flow {
        emitAll(getNetworkRequest())
    }

    companion object {
        const val OK_RESPONSE = 200
        const val NO_INTERNET = -1
    }
}