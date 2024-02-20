package com.example.tem.home.model

data class Agreement(
    val isAgreeIntervalNotification : Boolean
)

data class AgreementResponse(
    val id: Int,
    val userId: Int,
    val isAgreeIntervalNotification: Boolean,
    val createdAt: String,
    val updatedAt: String,
)
