package com.example.testovoe.domain

import com.example.testovoe.domain.api.PersonRepository
import com.example.testovoe.domain.api.PersonUseCase
import com.example.testovoe.domain.models.FailureType
import com.example.testovoe.domain.models.PersonPreview
import com.example.testovoe.domain.models.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PersonUseCaseImpl(private val personRepository: PersonRepository) : PersonUseCase {
    override fun getAllPersons(): Flow<Pair<List<PersonPreview>?, FailureType?>> {
        return personRepository.getAllPersons().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Failed -> {
                    Pair(null, result.message)
                }
            }
        }
    }

    override fun getPersonsFromInternet(): Flow<Pair<List<PersonPreview>?, FailureType?>> {
        return personRepository.getPersonsFromInternet().map { result ->
            when (result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Failed -> {
                    Pair(null, result.message)
                }
            }
        }
    }
}