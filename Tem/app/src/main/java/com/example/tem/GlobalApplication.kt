package com.example.tem

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

val NATIVE_APP_KEY = "0670cef2ebb2dba78e1fb26489487bb5"
class GlobalApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들

        // Kakao SDK 초기화
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }
}