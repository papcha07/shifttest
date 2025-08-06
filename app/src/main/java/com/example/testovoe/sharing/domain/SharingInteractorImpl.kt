package com.example.testovoe.sharing.domain

import com.example.testovoe.sharing.domain.api.SharingInteractor
import com.example.testovoe.sharing.domain.api.SharingRepository

class SharingInteractorImpl(private val sharingRepository: SharingRepository) : SharingInteractor {

    override fun openPhone(phoneNumber: String) {
        sharingRepository.openPhone(phoneNumber)
    }

    override fun openEmailClient(email: String) {
        sharingRepository.openEmailClient(email)
    }

    override fun openMap(address: String) {
        sharingRepository.openMap(address)
    }
}