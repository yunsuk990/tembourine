package com.example.tem.home

import android.app.Application
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tem.MainActivity
import com.example.tem.databinding.ActivityStartBinding
import com.example.tem.home.model.Agreement
import com.example.tem.home.model.AgreementResponse
import com.example.tem.home.model.Token
import com.example.tem.network.AuthApi
import com.example.tem.network.RetrofitService.getRetrofit
import com.example.tem.network.kakaoLoginResponse
import com.google.gson.Gson
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import com.kakao.sdk.user.model.UserServiceTerms
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class StartActivity : AppCompatActivity() {

    lateinit var binding: ActivityStartBinding
    lateinit var retrofit: Retrofit
    companion object {
        const val KAKAO_TAG = "Kakao"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofit = getRetrofit()

        var keyHash = Utility.getKeyHash(this)
        Toast.makeText(this@StartActivity, keyHash.toString(), Toast.LENGTH_LONG).show()

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
                retrofit.create(AuthApi::class.java).kakaoLogin(Token(token.accessToken)).enqueue(object: Callback<kakaoLoginResponse>{
                    override fun onResponse(
                        call: Call<kakaoLoginResponse>,
                        response: Response<kakaoLoginResponse>,
                    ) {
                        var res = response.body()
                        Log.d(KAKAO_TAG, res.toString())
                        getServiceTerms(res!!.token)

                    }

                    override fun onFailure(call: Call<kakaoLoginResponse>, t: Throwable) {
                        Log.d(KAKAO_TAG, t.message.toString())
                    }

                })
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

    private fun getServiceTerms(token: String) {
        UserApiClient.instance.serviceTerms { userServiceTerms, error ->
            if (error != null) {
                Log.e("kakao", "서비스 약관 동의 내역 확인하기 실패", error)
            } else if (userServiceTerms != null) {
                Log.i(
                    "kakao", "서비스 약관 동의 내역 확인하기 성공" +
                            "\n회원번호: ${userServiceTerms.id}" +
                            "\n동의한 약관: \n${userServiceTerms.serviceTerms?.joinToString("\n")}"
                )
                Log.d("kakao", userServiceTerms.toString())
                for(i in userServiceTerms.serviceTerms!!){
                    if(i.tag == "service_03"){
                        Log.d("serviceTem:retrofit_userServiceTerm", i.toString())
                        retrofit.create(AuthApi::class.java).postAgreement("Bearer $token", Agreement(i.agreed)).enqueue(object: Callback<AgreementResponse>{
                            override fun onResponse(
                                call: Call<AgreementResponse>,
                                response: Response<AgreementResponse>,
                            ) {
                                var res = response.body()
                                Log.d("serviceTem:retrofit", res.toString())
                            }

                            override fun onFailure(call: Call<AgreementResponse>, t: Throwable) {
                                Log.d("serviceTem:retrofit", t.message.toString())
                            }
                        })
                        break
                    }
                }
            }
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