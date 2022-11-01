package com.sms.pipe.view.profile

import androidx.lifecycle.*
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.domain.usecases.LogoutUseCase
import com.sms.pipe.view.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(private val logoutUseCase: LogoutUseCase ,private val getLoggedUserUseCase: GetLoggedUserUseCase) : BaseFragmentViewModel() {

    private val _loggedOut = MutableLiveData<Boolean>()
    val loggedOut:LiveData<Boolean> = _loggedOut

    val user: LiveData<UserModel> =  liveData(Dispatchers.IO) {
        getLoggedUserUseCase().collect{ user->
            user?.let {
                emit(it)
            }
        }
    }

    fun logout() {
        viewModelScope.launch(Dispatchers.IO){
            logoutUseCase().let {
                _loggedOut.postValue(it)
            }
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is profile Fragment"
    }
    val text: LiveData<String> = _text
}