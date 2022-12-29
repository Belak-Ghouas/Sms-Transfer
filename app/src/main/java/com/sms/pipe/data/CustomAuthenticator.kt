package com.sms.pipe.data

import com.sms.pipe.data.models.LogoutEvent
import com.sms.pipe.data.models.RefreshTokenResponse
import com.sms.pipe.domain.repositories.SecureDataStoreRepository
import com.sms.pipe.utils.GsonHelper.gson
import com.sms.pipe.utils.KEY_REFRESH_TOKEN
import com.sms.pipe.utils.KEY_TOKEN
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.greenrobot.eventbus.EventBus
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CustomAuthenticator(
    private val secureDataStore: SecureDataStoreRepository,
    private val eventBus: EventBus
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {

        return runBlocking {
            if (response.priorResponse?.code==401) {
                eventBus.post(LogoutEvent())
                return@runBlocking null
            }


            val service: ApiToken =  Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build().create(ApiToken::class.java)
            try {
                val res = service.refreshToken(secureDataStore.getString(KEY_REFRESH_TOKEN))
                if (res.isSuccessful) {
                    saveTokens(res.body())
                    val builder = response.request
                        .newBuilder().header("Authorization", "Bearer " + res.body()?.access_token)
                    return@runBlocking builder.build()
                }
            }catch (exception:Exception){
                eventBus.post(LogoutEvent())
                return@runBlocking null
            }

            eventBus.post(LogoutEvent())
            return@runBlocking null

        }

    }


    private fun saveTokens(response: RefreshTokenResponse?) {
        response?.access_token?.let { it1 -> secureDataStore.store(KEY_TOKEN, it1) }
        response?.refresh_token?.let { it1 -> secureDataStore.store(KEY_REFRESH_TOKEN, it1) }
    }

    private fun responseCount(response: Response): Int {
        var res = response
        var result = 1
        while (res.priorResponse?.also { res = it } != null) {
            result++
        }
        return result
    }
}