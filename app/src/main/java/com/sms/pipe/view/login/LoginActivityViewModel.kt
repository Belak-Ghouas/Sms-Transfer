package com.sms.pipe.view.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.SampleApplication.Companion.applicationScope
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.domain.usecases.LoginUseCase
import com.sms.pipe.domain.usecases.SignUpUseCase
import com.sms.pipe.utils.doIfFailure
import com.sms.pipe.utils.doIfSuccess
import com.sms.pipe.view.base.BaseActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivityViewModel(private val loginUseCase: LoginUseCase,
                             private val initMessagingUseCase: InitMessagingUseCase,
                             private val signUpUseCase : SignUpUseCase
                             ): BaseActivityViewModel() {

    private val _isLogged=MutableLiveData<Boolean>()
    val isLogged :LiveData<Boolean> = _isLogged

    private val _loading = MutableLiveData<Boolean>()
    val loading:LiveData<Boolean> = _loading

    private val _error = MutableLiveData<Int>()
    val error:LiveData<Int> = _error

    private val _errorSignUp = MutableLiveData<Int>()
    val errorSignUp:LiveData<Int> = _errorSignUp

    private val _isRegistered=MutableLiveData<Boolean>()
    val isRegistered :LiveData<Boolean> = _isRegistered



    fun login(email:String, password:String){
        _loading.value=true
        applicationScope.launch(Dispatchers.IO) {

            val result  = loginUseCase(email, password)
            _loading.postValue(false)

            result.doIfSuccess {
                applicationScope.launch {
                    initMessagingUseCase()
                }
                _isLogged.postValue(true)
            }.doIfFailure{ errorCode, _ ->
                _error.postValue(errorCode)
            }
        }
    }


    fun signUp(email:String,username:String, password:String){
        _loading.value=true
        applicationScope.launch(Dispatchers.IO){
            signUpUseCase(email,username,password).doIfSuccess {
                _loading.postValue(false)
                _isRegistered.postValue(true)
            }.doIfFailure { errorCode, _ ->
                _loading.postValue(false)
                _errorSignUp.postValue(errorCode)
            }
        }
    }
}