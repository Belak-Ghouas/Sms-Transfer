package com.sms.pipe.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.GetAppletsUseCase
import com.sms.pipe.domain.usecases.RefreshUserDataUseCase
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.view.base.BaseFragmentViewModel
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val sendMessageUseCase: SendMessageUseCase,
                    private val getAppletsUseCase: GetAppletsUseCase,
                    private val refreshUserDataUseCase: RefreshUserDataUseCase
) : BaseFragmentViewModel() {

    private val _appletUi = MutableLiveData<AppletUi>()
    val appletUi : LiveData<AppletUi> =_appletUi


    fun getApplet(){
        viewModelScope.launch(Dispatchers.Default){
            getAppletsUseCase().firstOrNull()?.let {
                _appletUi.postValue(it)
            }
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO){
            refreshUserDataUseCase()
            getUser()
        }
    }

    private val _hasSlack  = MutableLiveData<Boolean> ()
    val hasSlack:LiveData<Boolean> = _hasSlack

    init {
        getUser()
    }

    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO){
            getLoggedUserUseCase()?.let {
                _hasSlack.postValue(!it.slack_access_token.isNullOrEmpty())
            }?:run {
                _hasSlack.postValue(false)
            }
        }
    }


}