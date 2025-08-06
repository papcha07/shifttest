package com.example.testovoe.domain.api

import com.example.testovoe.domain.models.FailureType
import com.example.testovoe.domain.models.PersonPreview
import kotlinx.coroutines.flow.Flow

interface PersonUseCase {
    fun getAllPersons(): Flow<Pair<List<PersonPreview>?, FailureType?>>
    fun getPersonsFromInternet(): Flow<Pair<List<PersonPreview>?, FailureType?>>
}
