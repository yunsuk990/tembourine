package com.example.tem
import android.app.Application
import android.os.Build
import com.kakao.sdk.common.KakaoSdk

val NATIVE_APP_KEY = "8d0224465396e18d2a695278c835817b"
class GlobalApplication : Application() {

    companion object {
        lateinit var prefManager: SharedPrefManager
    }
    override fun onCreate() {
        super.onCreate()
        // 다른 초기화 코드들
        prefManager = SharedPrefManager(applicationContext)

        // Kakao SDK 초기화
        KakaoSdk.init(this, NATIVE_APP_KEY)
    }
}