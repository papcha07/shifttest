package com.example.testovoe.domain.api

import com.example.testovoe.domain.models.PersonPreview
import com.example.testovoe.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface PersonRepository {
    fun getAllPersons(): Flow<Resource<List<PersonPreview>>>
    fun getPersonsFromInternet(): Flow<Resource<List<PersonPreview>>>
}
