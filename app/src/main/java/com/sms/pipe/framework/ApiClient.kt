package com.sms.pipe.framework

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASEURL = "https://sms-pipe-web.web.app"
class ApiClient {
    companion object{
        private var retrofit: Retrofit?=null
        fun getApiClient(): Retrofit {
            retrofit?.let {
               return it
            }?: kotlin.run {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASEURL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                return retrofit!!
            }

        }
    }
}