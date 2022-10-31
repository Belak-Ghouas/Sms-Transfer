package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.utils.Result

class SignUpUseCase (private val userRepository: UserRepository) {

    suspend operator fun invoke(email:String,username:String, password:String):Result<Unit>{
        return userRepository.signUp(email,username,password)
    }
}
