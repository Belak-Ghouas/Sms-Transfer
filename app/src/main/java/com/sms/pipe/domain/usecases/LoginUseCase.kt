package com.sms.pipe.domain.usecases

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository

class LoginUseCase (private val userRepository: UserRepository){

    suspend operator fun invoke(email:String , password:String): UserModel? {
          return  userRepository.login(email,password)
    }
}
