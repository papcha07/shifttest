package com.example.testovoe.data.models

class PersonRequest(
    val results: Int = 10
) {
    fun toQueryMap(): Map<String, String> {
        val map = mutableMapOf<String, String>()
        map["results"] = results.toString()
        return map
    }
}