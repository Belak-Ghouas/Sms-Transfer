package com.sms.pipe.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.LogoutUseCase
import com.sms.pipe.view.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val logoutUseCase: LogoutUseCase) : BaseFragmentViewModel() {

    private val _loggedOut = MutableLiveData<Boolean>()
    val loggedOut:LiveData<Boolean> = _loggedOut
    fun logout() {
        viewModelScope.launch(Dispatchers.IO){
           _loggedOut.postValue(logoutUseCase())
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text
}