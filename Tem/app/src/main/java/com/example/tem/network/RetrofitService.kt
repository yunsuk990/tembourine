package com.example.tem.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    private var instance: Retrofit? = null
    private const val BASE_URL = "http://temburine-dev-env.eba-pqgquykt.ap-northeast-2.elasticbeanstalk.com"

    fun getRetrofit(): Retrofit {
        if(instance == null){
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return instance!!
    }

}