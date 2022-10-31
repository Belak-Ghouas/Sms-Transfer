package com.sms.pipe.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sms.pipe.domain.usecases.GetOnBoardingStepsUseCase
import com.sms.pipe.view.base.BaseFragmentViewModel
import com.sms.pipe.view.model.Step

class HomeViewModel(
    private val getOnBoardingStepsUseCase : GetOnBoardingStepsUseCase,
) : BaseFragmentViewModel() {


    private val _steps = MutableLiveData<List<Step>>()
    val steps: LiveData<List<Step>> = _steps


    fun getSteps(){
        _steps.value = getOnBoardingStepsUseCase()
    }

}