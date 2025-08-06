package com.example.testovoe.sharing.domain.api

interface SharingInteractor {
    fun openPhone(phoneNumber: String)
    fun openEmailClient(email: String)
    fun openMap(address: String)
}
