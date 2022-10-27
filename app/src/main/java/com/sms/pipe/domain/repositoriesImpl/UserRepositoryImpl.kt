package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.SecureDataStore
import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.utils.doIfSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource, private val userLocalDataSource: UserLocalDataSource, private val secureDataStore: SecureDataStore) :
    UserRepository {
    private val scope = CoroutineScope(Job()+Dispatchers.IO)

    override suspend fun login(email: String, password: String): UserModel?{
      userRemoteDataSource.login(email,password).doIfSuccess {
          saveUserOnLocal(it)
          return it
      }
        return null
    }


    override suspend fun getLoggedUser(): Flow<UserModel?> {
       return userLocalDataSource.getLoggedUser()
    }

    override suspend fun logout() = userLocalDataSource.logout()

    override suspend fun refreshData() {
        secureDataStore.get("token").apply {
            if(this.isNotEmpty()){
                userRemoteDataSource.refreshData(this).doIfSuccess {
                    saveUserOnLocal(it)
                }
            }
        }

    }

    private fun saveUserOnLocal(user: UserModel){
        scope.launch {
            userLocalDataSource.save(user)
            user.token?.let { secureDataStore.store("token", it) }
        }
    }
}