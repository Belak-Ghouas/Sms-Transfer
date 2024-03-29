package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.SecureDataStore
import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.utils.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserRepositoryImpl(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val secureDataStore: SecureDataStore
) : UserRepository {

    private val scope = CoroutineScope(Job() + Dispatchers.IO)

    override suspend fun login(email: String, password: String): Result<UserModel> {
        userRemoteDataSource.login(email, password).apply {
            this.doIfSuccess {
                saveUserOnLocal(it)
            }
            return this
        }
    }

    override suspend fun signUp(email: String, username: String, password: String) =
        userRemoteDataSource.signUp(email, username, password)


    override suspend fun getLoggedUser(): Flow<UserModel?> {
        return userLocalDataSource.getLoggedUser()
    }

    override suspend fun getUser(): UserModel? {
        return userLocalDataSource.getUser()
    }

    override fun getUserSession(): UserModel? {
        return userLocalDataSource.userSession()
    }

    override suspend fun logout() = userLocalDataSource.logout()


    override suspend fun refreshData(token: String) {
        userRemoteDataSource.refreshData(token).doIfSuccess {
            saveUserOnLocal(it)
        }
    }

    override suspend fun deleteAccount(token: String): Result<Unit> {
       return userRemoteDataSource.deleteAccount(token).doIfSuccess {
            userLocalDataSource.deleteAccount(token)
        }
    }

    private fun saveUserOnLocal(user: UserModel) {
        scope.launch {
            userLocalDataSource.save(user)
            user.token?.let { secureDataStore.store(KEY_TOKEN, it) }
        }
    }


    /*override suspend fun refreshToken(token: String): Result<RefreshTokenResponse> {
        secureDataStore.getString(KEY_REFRESH_TOKEN).let {
            val result = userRemoteDataSource.refreshToken(token)
            result.doIfSuccess {
                it.access_token?.let { it1 -> secureDataStore.store(KEY_TOKEN, it1) }
                it.refresh_token?.let { it1 -> secureDataStore.store(KEY_REFRESH_TOKEN, it1) }
            }
            return result
        }
    }*/
}