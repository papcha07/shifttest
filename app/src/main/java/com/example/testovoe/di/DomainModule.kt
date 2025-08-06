package com.example.testovoe.di

import com.example.testovoe.domain.PersonUseCaseImpl
import com.example.testovoe.domain.api.PersonUseCase
import com.example.testovoe.sharing.domain.SharingInteractorImpl
import com.example.testovoe.sharing.domain.api.SharingInteractor
import org.koin.dsl.module

val domainModule = module{
    single<PersonUseCase>{
        PersonUseCaseImpl(
            personRepository = get()
        )
    }

    single<SharingInteractor> {
        SharingInteractorImpl(
            sharingRepository = get()
        )
    }
}