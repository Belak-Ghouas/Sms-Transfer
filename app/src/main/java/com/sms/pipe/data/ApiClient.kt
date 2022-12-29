package com.sms.pipe.data

import com.google.gson.GsonBuilder
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val BASEURL = "https://sms-pipe-web.web.app"

class ApiClient {
    companion object {
        private var retrofit: Retrofit? = null
        fun getApiClient(authenticator: Authenticator): Retrofit {
            retrofit?.let {
                return it
            } ?: kotlin.run {
                val gson = GsonBuilder()
                    .setLenient()
                    .create()
                val okHttpClient = OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .authenticator(authenticator)
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