package com.example.testovoe.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testovoe.domain.api.PersonUseCase
import com.example.testovoe.domain.models.FailureType
import com.example.testovoe.domain.models.PersonPreview
import com.example.testovoe.sharing.domain.api.SharingInteractor
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PersonViewModel(
    private val personUseCase: PersonUseCase,
    private val sharingInteractor: SharingInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<PersonState>(PersonState.Loading)
    val state: StateFlow<PersonState> = _state.asStateFlow()

    init {
        loadPersons()
    }

    private fun loadPersons() {
        viewModelScope.launch {
            personUseCase.getAllPersons()
                .collect { (persons, error) ->
                    setPersonState(persons, error)
                }
        }
    }

    private fun setPersonState(persons: List<PersonPreview>?, error: FailureType?) {
        if (persons != null) {
            _state.value = PersonState.Content(persons)
        } else if (error != null) {
            _state.value = PersonState.Error(error)
        }
    }

    fun getPersonFromInternet() {
        viewModelScope.launch {
            _state.value = PersonState.Loading
            personUseCase.getPersonsFromInternet().collect { (persons, error) ->
                setPersonState(persons, error)
            }
        }
    }

    fun openPhone(phone: String) {
        sharingInteractor.openPhone(phone)
    }

    fun openEmail(email: String) {
        sharingInteractor.openEmailClient(email)
    }

    fun openMap(address: String) {
        sharingInteractor.openMap(address)
    }
}