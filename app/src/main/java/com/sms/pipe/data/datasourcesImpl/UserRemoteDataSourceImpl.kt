package com.sms.pipe.data.datasourcesImpl

import android.util.Log
import com.google.gson.Gson
import com.sms.pipe.data.ApiInterface
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.models.ErrorApi
import com.sms.pipe.data.models.RefreshTokenResponse
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.utils.ErrorCodes.EMPTY_API_RESPONSE
import com.sms.pipe.utils.ErrorCodes.INTERNAL_ERROR
import com.sms.pipe.utils.ErrorCodes.NO_NETWORK
import com.sms.pipe.utils.PhoneUtils
import com.sms.pipe.utils.Result
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.json.JSONObject
import retrofit2.Response


class UserRemoteDataSourceImpl(private val userApi : ApiInterface, private val phoneUtils: PhoneUtils) :
    UserRemoteDataSource {
    
    override suspend fun login(email: String, password: String): Result<UserModel> {
        try {
            return if (phoneUtils.isNetworkOnline()) {
                val loginResponse = userApi.login(email, password)
                if (loginResponse.isSuccessful) {
                    loginResponse.body()?.user?.let {
                        it.token = loginResponse.body()?.token
                        it.logged = true
                        Result.Success(it)
                    } ?: kotlin.run {
                        Result.Failure(EMPTY_API_RESPONSE, "empty response")
                    }
                } else {

                    val errorBody: String? = loginResponse.errorBody()?.string()
                    val jsonObject = errorBody?.let { JSONObject(it) }
                    val errorApi = Gson().fromJson(jsonObject?.get("error").toString(), ErrorApi::class.java)
                    Result.Failure(errorApi.errorCode, errorApi.message)
                }
            } else {

                Result.Failure(errorCode = NO_NETWORK, "no network")
            }
        }catch (exception:Exception){
            Log.e("UserRemoteDataSource"," exception : $exception")
          return   Result.Failure(errorCode = INTERNAL_ERROR, "no network")
        }

    }

    override suspend fun signUp(email: String, username: String, password: String) :Result<Unit> {
        try {
            return if (phoneUtils.isNetworkOnline()) {
                val loginResponse = userApi.signUp(email, password,username)
                if (loginResponse.isSuccessful) {
                    Result.Success(Unit)
                } else {

                    val errorBody: String? = loginResponse.errorBody()?.string()
                    val jsonObject = errorBody?.let { JSONObject(it) }
                    val errorApi = Gson().fromJson(jsonObject?.get("error").toString(), ErrorApi::class.java)
                    Result.Failure(errorApi.errorCode, errorApi.message)
                }
            } else {

                Result.Failure(errorCode = NO_NETWORK, "no network")
            }
        }catch (exception:Exception){
            return   Result.Failure(errorCode = INTERNAL_ERROR, "no network")
        }
    }


    override suspend fun save(user: UserModel) {
    }

    override suspend fun refreshData(token:String): Result<UserModel> {
        try {
            return if (phoneUtils.isNetworkOnline()) {
                val loginResponse = userApi.refreshData("Bearer $token")
                if (loginResponse.isSuccessful) {
                    loginResponse.body()?.user?.let {
                        it.logged = true
                        Result.Success(it)
                    } ?: kotlin.run {
                        Result.Failure(EMPTY_API_RESPONSE, "empty response")
                    }
                } else {
                    Result.Failure(loginResponse.code(), loginResponse.errorBody().toString())
                }
            } else {

                Result.Failure(errorCode = NO_NETWORK, "no network")
            }
        }catch (exception:Exception){
            return   Result.Failure(errorCode = NO_NETWORK, "no network")
        }
    }
/*

    override suspend fun refreshToken(refresh_token: String): Result<RefreshTokenResponse> {
        try {
            return if (phoneUtils.isNetworkOnline()) {
                val refreshTokenResponse = userApi.refreshToken(refresh_token)
                if (refreshTokenResponse.isSuccessful) {
                    refreshTokenResponse.body()?.let {
                        Result.Success(it)
                    }?:run{
                        Result.Failure(EMPTY_API_RESPONSE, "empty response")
                    }

                } else {
                    Result.Failure(refreshTokenResponse.code(), refreshTokenResponse.errorBody().toString())
                }
            } else {

                Result.Failure(errorCode = NO_NETWORK, "no network")
            }
        }catch (exception:Exception){
            return   Result.Failure(errorCode = NO_NETWORK, "no network")
        }
    }

*/
}