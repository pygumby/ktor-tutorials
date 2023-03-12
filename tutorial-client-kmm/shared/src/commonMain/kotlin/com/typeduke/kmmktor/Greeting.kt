package com.typeduke.kmmktor

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

class Greeting {
    private val client = HttpClient()

    suspend fun greeting(): String {
        val response = this.client.get("https://ktor.io/docs/")
        return response.bodyAsText()
    }
}
