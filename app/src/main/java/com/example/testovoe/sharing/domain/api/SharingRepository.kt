package com.example.testovoe.sharing.domain.api

interface SharingRepository {
    fun openPhone(phoneNumber: String)
    fun openEmailClient(email: String)
    fun openMap(address: String)
}