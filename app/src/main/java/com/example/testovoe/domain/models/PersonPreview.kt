package com.example.testovoe.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PersonPreview(
    val id: String,
    val first: String,
    val last: String,
    val email: String,
    val phone: String,
    val thumbnail: String,
    val streetNumber: String,
    val streetName: String,
    val city: String,
    val country: String
) : Parcelable

