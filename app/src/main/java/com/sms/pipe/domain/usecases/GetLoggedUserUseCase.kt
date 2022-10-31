package com.sms.pipe.domain.usecases

import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.view.model.Step
import com.sms.pipe.view.model.StepStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

class GetLoggedUserUseCase(private val userRepository: UserRepository ,
                           private val updateStepsUseCase: UpdateStepsUseCase,
                           private val getStepsUseCase: GetOnBoardingStepsUseCase,
                            private val initMessagingUseCase: InitMessagingUseCase) {

    suspend operator fun invoke(): Flow<UserModel?> {
       return userRepository.getLoggedUser().onEach {
           it?.slack_access_token?.let {
              updateSteps()
               initMessagingUseCase()
           }
       }
    }

    private fun updateSteps(){
      val steps =  getStepsUseCase().toMutableList()
        if(steps.isNotEmpty()){
            steps[1]= Step(1,StepStatus.DONE)
            updateStepsUseCase(steps)
        }

    }
}
