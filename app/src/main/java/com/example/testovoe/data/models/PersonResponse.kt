package com.example.testovoe.data.models

data class PersonResponse(
    val results: List<PersonDto>
) : Response()

data class PersonDto(
    val login: LoginDto,
    val name: NameDto,
    val location: LocationDto,
    val phone: String,
    val picture: PictureDto,
    val email : String
)

data class NameDto(
    val first: String,
    val last: String
)

data class LocationDto(
    val street: StreetDto,
    val country: String,
    val city: String
)

data class StreetDto(
    val number: Int,
    val name: String
)

data class PictureDto(
    val large: String
)

data class LoginDto(
    val uuid: String
)