package com.sms.pipe.data.datasourcesImpl

import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.data.ApiInterface
import com.sms.pipe.utils.ErrorCodes.EMPTY_API_RESPONSE
import com.sms.pipe.utils.ErrorCodes.NO_NETWORK
import com.sms.pipe.utils.PhoneUtils
import com.sms.pipe.utils.Result

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
                    Result.Failure(loginResponse.code(), loginResponse.errorBody().toString())
                }
            } else {

                Result.Failure(errorCode = NO_NETWORK, "no network")
            }
        }catch (exception:Exception){
          return   Result.Failure(errorCode = NO_NETWORK, "no network")
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


}