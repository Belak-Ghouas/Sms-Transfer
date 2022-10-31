package com.sms.pipe.domain.usecases

import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.view.model.AppletUi
import com.sms.pipe.view.model.Step
import com.sms.pipe.view.model.StepStatus

class StoreAppletUseCase(
    private val appletRepository: AppletRepository,
    private val updateStepsUseCase: UpdateStepsUseCase,
    private val getStepsUseCase: GetOnBoardingStepsUseCase
) {

    suspend operator fun invoke(applet: AppletUi): Boolean {
        appletRepository.storeApplet(applet).let {
            if (it) {
                updateSteps()
            }
            return it
        }
    }


    private fun updateSteps() {
        val steps = getStepsUseCase().toMutableList()
        if (steps.isNotEmpty()) {
            steps[2] = Step(2, StepStatus.DONE)
            updateStepsUseCase(steps)
        }

    }
}
