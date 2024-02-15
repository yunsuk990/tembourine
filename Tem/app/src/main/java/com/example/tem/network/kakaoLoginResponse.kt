package com.example.tem.network

data class kakaoLoginResponse(
    val user: User,
    val token: String = ""
)

data class User(
    val id: Int,
    val authType: String,
    val authId: String,
    val createdAt: String,
    val deletedAt: String
)
