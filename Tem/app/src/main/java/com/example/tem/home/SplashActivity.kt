package com.example.tem.home

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.se.omapi.Session
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ReportFragment.Companion.reportFragment
import com.example.tem.MainActivity
import com.example.tem.PushPermissionDialogFragment
import com.example.tem.databinding.ActivitySplashBinding
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient


class SplashActivity : AppCompatActivity() {

    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //카카오 로그아웃
        UserApiClient.instance.logout {  }
        checkPermission()
    }

    private fun checkKakaoLogin(){
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                Log.d("checkKakaoLogin", tokenInfo.toString())
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        //로그인 필요
                        startActivity(Intent(this@SplashActivity, StartActivity::class.java))
                    }
                    Log.d("error", error.toString())
                    startActivity(Intent(this@SplashActivity, StartActivity::class.java))
//                    else {
//                        //기타 에러
//                    }
                }
                else {
                    //토큰 유효성 체크 성공(필요 시 토큰 갱신됨)
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))

                    //카카오 사용자 정보 업데이트
                }
            }
        }
        else {
            //로그인 필요
            startActivity(Intent(this@SplashActivity, StartActivity::class.java))
        }
    }

    // 권한 설정 후 반환 값에 따른 분기 처리
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray,
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if(requestCode == 10){
//            Log.d("request", requestCode.toString())
//
//            // 권한 승인된 경우
//            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
////                Toast.makeText(this, "알림 설정 허가", Toast.LENGTH_SHORT).show()
//                startActivity(Intent(this@SplashActivity, StartActivity::class.java))
//            }else{
//                // 권한 불허 경우
//                TedPermission.create()
//                    .setPermissionListener(object: PermissionListener{
//                        override fun onPermissionGranted() {
//                            checkKakaoLogin()
//                        }
//
//                        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//                            checkPermission()
//                        }
//                    })
//                    .setDeniedMessage("푸시 알림 권한을 허용해야 이용 가능합니다.")
//                    .setPermissions(Manifest.permission.POST_NOTIFICATIONS)
//                    .check()
//            }
//        }
//    }

    // 권한 확인
    fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("check", "true")
//            // Android 버전 확인
//            // Android 13 이후 버전은 권한 요청 창 생성
//            // Android 12 이하는 권한 요청 창 생성되지 않음 -> 유저가 설정을 하도록 유도
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
//                    10
//                )
//            } else {
//                // Android 12이하 버전일 경우 -> 알림 채널을 만들어야함
//
//            }

            //푸시 알림 창 커스텀화
            showPushPermissionDialogFragment()
        } else {
            // 권한 설정이 된 경우
            checkKakaoLogin()
        }
    }

    private fun showPushPermissionDialogFragment(){
        PushPermissionDialogFragment().show(supportFragmentManager, PushPermissionDialogFragment.TAG)
    }
}