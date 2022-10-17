package com.sms.pipe.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.usecases.GetAppletsUseCase
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.view.model.AppletUi
import com.sms.pipe.view.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val sendMessageUseCase: SendMessageUseCase,
                    private val getAppletsUseCase: GetAppletsUseCase,
                    private val getLoggedUserUseCase: GetLoggedUserUseCase) : BaseFragmentViewModel() {

    private val _appletUi = MutableLiveData<AppletUi>()
    val appletUi : LiveData<AppletUi> =_appletUi
    fun sendMessage(message:String) {
        viewModelScope.launch(Dispatchers.IO){
            sendMessageUseCase(message,"#test-android-app")
        }

    }


    fun getApplet(){
        viewModelScope.launch(Dispatchers.Default){
            getAppletsUseCase().firstOrNull()?.let {
                _appletUi.postValue(it)
            }
        }
    }

}