package com.sms.pipe.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.LoginUseCase
import com.sms.pipe.view.base.BaseActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityViewModel(private val loginUseCase: LoginUseCase): BaseActivityViewModel() {

    private val _isLogged=MutableLiveData<Boolean>()
    val isLogged :LiveData<Boolean> = _isLogged

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> = _loading

    fun login(email:String, password:String){
        _loading.value=true
        viewModelScope.launch(Dispatchers.IO) {

            loginUseCase(email, password).also {
                _loading.postValue(false)
                _isLogged.postValue(it!=null)
            }
        }
    }
}