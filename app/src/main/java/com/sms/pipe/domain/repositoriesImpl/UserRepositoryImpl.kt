package com.sms.pipe.domain.repositoriesImpl

import com.sms.pipe.data.datasources.UserLocalDataSource
import com.sms.pipe.data.datasources.UserRemoteDataSource
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.utils.doIfSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class UserRepositoryImpl(private val userRemoteDataSource: UserRemoteDataSource, private val userLocalDataSource: UserLocalDataSource) :
    UserRepository {
    private val scope = CoroutineScope(Job()+Dispatchers.IO)

    override suspend fun login(email: String, password: String): UserModel?{
      userRemoteDataSource.login(email,password).doIfSuccess {
          saveUserOnLocal(it)
          return it
      }
        return null
    }


    override suspend fun getLoggedUser(): UserModel? {
       return userLocalDataSource.getLoggedUser()
    }

    override suspend fun logout() = userLocalDataSource.logout()

    private fun saveUserOnLocal(user: UserModel){
        scope.launch {
            userLocalDataSource.save(user)
        }
    }
}