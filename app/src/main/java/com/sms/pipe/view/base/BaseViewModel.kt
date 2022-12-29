package com.sms.pipe.view.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.LogoutUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

abstract class BaseViewModel : ViewModel() {

    private val logoutUseCase : LogoutUseCase by inject(LogoutUseCase::class.java)

    private val _loggedOut = MutableLiveData<Boolean>()
    val loggedOut: LiveData<Boolean> = _loggedOut



    fun logout() {
        viewModelScope.launch(Dispatchers.IO){
            logoutUseCase().let {
                _loggedOut.postValue(it)
            }
        }
    }
}