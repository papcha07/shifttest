package com.example.testovoe.presenter

import com.example.testovoe.domain.models.FailureType
import com.example.testovoe.domain.models.PersonPreview

sealed class PersonState {
    data object Loading : PersonState()
    data class Content(val persons: List<PersonPreview>) : PersonState()
    data class Error(val errorType: FailureType) : PersonState()
}