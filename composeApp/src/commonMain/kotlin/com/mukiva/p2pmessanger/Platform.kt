package com.mukiva.p2pmessanger

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform