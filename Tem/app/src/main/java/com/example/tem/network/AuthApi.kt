package com.example.tem.network

import com.example.tem.home.model.Agreement
import com.example.tem.home.model.AgreementResponse
import com.example.tem.home.model.Token
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {

    //카카오 로그인
    @POST("/auth/authenticate/kakao")
    fun kakaoLogin(
        @Body accessKey: Token
    ): Call<kakaoLoginResponse>


    @POST("/users/me/agreement")
    fun postAgreement(
        @Header("Authorization") token: String,
        @Body isAgreeIntervalNotification: Agreement
    ): Call<AgreementResponse>

    @PATCH("/users/me/agreement")
    fun patchAgreement(
        @Header("Authorization") token: String,
        @Body isAgreeIntervalNotification: Agreement
    ): Call<AgreementResponse>


}