package com.sms.pipe.view.activities.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.utils.doIfSuccess
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val sendMessageUseCase: SendMessageUseCase, private val messagingRepository: MessagingRepository) : BaseFragmentViewModel() {

    fun sendMessage(message:String) {
        viewModelScope.launch(Dispatchers.IO){
            sendMessageUseCase(message,"#test-android-app")
        }

    }

    private val _text = MutableLiveData<String>().apply {
        viewModelScope.launch(Dispatchers.IO){
            messagingRepository.getConversationList().doIfSuccess {
               this@apply.postValue(it.toString())
            }
        }


    }
    val text: LiveData<String> = _text
}