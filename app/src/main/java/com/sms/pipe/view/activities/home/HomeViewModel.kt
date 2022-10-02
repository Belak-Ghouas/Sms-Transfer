package com.sms.pipe.view.activities.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.slack.api.model.admin.App
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.view.model.AppletUi
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val sendMessageUseCase: SendMessageUseCase,
                    private val messagingRepository: MessagingRepository,
                    private val getLoggedUserUseCase: GetLoggedUserUseCase) : BaseFragmentViewModel() {

    private val _appletUi = MutableLiveData<AppletUi>()
    val appletUi : LiveData<AppletUi> =_appletUi
    fun sendMessage(message:String) {
        viewModelScope.launch(Dispatchers.IO){
            sendMessageUseCase(message,"#test-android-app")
        }

    }

    private val _text = MutableLiveData<String>().apply {
        viewModelScope.launch(Dispatchers.IO){
         /*   messagingRepository.getConversationList().doIfSuccess {
               this@apply.postValue(it.toString())
            }*/
        }


    }
    val text: LiveData<String> = _text

    fun getApplet(){
        viewModelScope.launch(Dispatchers.Default){
            ( getLoggedUserUseCase()?.toAppletUi())?.let {
                _appletUi.postValue(it)
            }
        }
    }


    private fun UserModel.toAppletUi(): AppletUi? {
        if (this.slack_team != null && this.slack_team.teamName != null) {
            return AppletUi(
                appletName = this.slack_team.teamName,
            )
        }
        return null
    }
}