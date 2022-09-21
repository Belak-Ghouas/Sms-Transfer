package com.sms.pipe.view.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SplashScreenActivityViewModel(private val getLoggedUserUseCase : GetLoggedUserUseCase):
    BaseActivityViewModel() {

    private val _isLogged= MutableLiveData<Boolean>()
    val isLogged : LiveData<Boolean> = _isLogged

    fun getLoggedUser(){
        viewModelScope.launch(Dispatchers.Default){
            _isLogged.postValue( getLoggedUserUseCase()!=null)
        }
    }
}