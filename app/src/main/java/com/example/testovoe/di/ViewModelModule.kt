package com.example.testovoe.di

import com.example.testovoe.presenter.PersonViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module { 
    viewModel { 
        PersonViewModel(
            personUseCase = get(),
            sharingInteractor = get()
        )
    }
}