package com.sms.pipe.data

import com.google.gson.GsonBuilder
import com.sms.pipe.BuildConfig
import okhttp3.Authenticator
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

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
                    .baseUrl(BuildConfig.BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()
                return retrofit!!
            }

        }
    }
}