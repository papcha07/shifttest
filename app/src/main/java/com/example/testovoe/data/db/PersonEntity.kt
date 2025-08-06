package com.example.testovoe.data.network

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.testovoe.domain.models.PersonPreview

@Entity(tableName = "person_table")
data class PersonEntity(
    @PrimaryKey
    val id: String,
    val first: String,
    val last: String,
    val streetNumber: String,
    val streetName: String,
    val city: String,
    val country: String,
    val thumbnail: String,
    val phone: String,
    val email: String
)

fun PersonPreview.toEntity(): PersonEntity {
    return PersonEntity(
        id = this.id,
        first = this.first,
        last = this.last,
        streetNumber = this.streetNumber,
        streetName = this.streetName,
        city = this.city,
        country = this.country,
        thumbnail = this.thumbnail,
        phone = this.phone,
        email = this.email
    )
}

fun PersonEntity.toPreview(): PersonPreview {
    return PersonPreview(
        id = this.id,
        first = this.first,
        last = this.last,
        streetNumber = this.streetNumber,
        streetName = this.streetName,
        city = this.city,
        country = this.country,
        thumbnail = this.thumbnail,
        phone = this.phone,
        email = this.email
    )
}