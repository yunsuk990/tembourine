package com.example.tem.home

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat.PermissionCompatDelegate
import com.example.tem.MainActivity
import com.example.tem.databinding.ActivityStartBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.User

class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding
    companion object {
        const val KAKAO_TAG = "Kakao"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.kakaoBtn.setOnClickListener {
            kakaoLogin()
        }
    }

    private fun kakaoLogin(){

        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.d(KAKAO_TAG, "카카오계정으로 로그인 실패", error)
            } else if (token != null) {
                Log.d(KAKAO_TAG, "카카오계정으로 로그인 성공 ${token}")
                getKakaoUserInfo()
                getKakaoTokenInfo()
                startActivity(Intent(this@StartActivity, MainActivity::class.java))
            }
        }

        // 카카오톡 앱 설치된 경우
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(this@StartActivity)){
            UserApiClient.instance.loginWithKakaoTalk(this@StartActivity) { token, error ->
                if (error != null) {
                    Log.e(KAKAO_TAG, "로그인 실패", error)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this@StartActivity, callback = callback)
                }
                else if (token != null) {
                    Log.i(KAKAO_TAG, "로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.loginWithKakaoAccount(this@StartActivity, callback = callback)
                }
            }
        }else{
            // 웹 카카오계정 접속
            UserApiClient.instance.loginWithKakaoAccount(this@StartActivity, callback = callback)
        }
    }

    // 카카오 사용자 정보 가져오기
    private fun getKakaoUserInfo(){
        UserApiClient.instance.me { user, error ->
            if(error != null){
                Log.e(KAKAO_TAG, "사용자 정보 요청 실패", error)
            }else if(user != null){
                Log.d(KAKAO_TAG,
                        "사용자 정보 요청 성공" +
                            "\n회원번호: ${user.id}" +
                            "\n이메일: ${user.kakaoAccount?.email}" +
                            "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                            "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}")

                // 사용자 데이터 서버로 전송
            }
        }
    }

    // 카카오 사용자 로그인 토큰 정보 가져오기
    private fun getKakaoTokenInfo() {
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
                Log.d(KAKAO_TAG, "토큰 정보 보기 실패", error)
            }
            else if (tokenInfo != null) {
                Log.i(KAKAO_TAG,
                        "토큰 정보 보기 성공" +
                            "\n회원번호: ${tokenInfo.id}" +
                            "\n만료시간: ${tokenInfo.expiresIn} 초" +
                            "\n $tokenInfo")
            }
        }
    }
}