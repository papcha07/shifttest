package com.example.testovoe.di

import androidx.room.Room
import com.example.testovoe.data.PersonRepositoryImpl
import com.example.testovoe.data.network.AppDatabase
import com.example.testovoe.data.network.NetworkClient
import com.example.testovoe.data.network.RetrofitClient
import com.example.testovoe.data.network.RetrofitNetworkClient
import com.example.testovoe.domain.api.PersonRepository
import com.example.testovoe.sharing.data.SharingRepositoryImpl
import com.example.testovoe.sharing.domain.api.SharingRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.practicum.android.diploma.util.InternetConnectionChecker

val dataModule = module {
    single<NetworkClient> {
        RetrofitNetworkClient(
            service = get(),
            connectionChecker = get()
        )
    }

    single<RetrofitClient> {
        RetrofitClient
    }

    single {
        InternetConnectionChecker(androidContext())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            AppDatabase::class.java,
            "persons.db"
        ).build()
    }

    single {
        get<AppDatabase>().personDao()
    }

    single<PersonRepository> {
        PersonRepositoryImpl(
            networkClient = get(),
            personDao = get()
        )
    }

    single<SharingRepository> {
        SharingRepositoryImpl(
            context = androidContext()
        )
    }
}