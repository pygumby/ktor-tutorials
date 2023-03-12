package com.typeduke.kmmktor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform