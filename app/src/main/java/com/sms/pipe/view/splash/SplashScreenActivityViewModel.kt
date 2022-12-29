package com.sms.pipe.view.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.GetUserOnceUseCase
import com.sms.pipe.view.base.BaseActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenActivityViewModel(private val getUserOnceUseCase: GetUserOnceUseCase) :
    BaseActivityViewModel() {

    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> = _isLogged

    fun getLoggedUser() {
        viewModelScope.launch(Dispatchers.Default) {
            getUserOnceUseCase().let {
                _isLogged.postValue(it != null)
            }
        }
    }
}