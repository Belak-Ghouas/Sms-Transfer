package com.sms.pipe.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.DeleteAppletUseCase
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.domain.usecases.RefreshUserDataUseCase
import com.sms.pipe.view.base.BaseActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val refreshUserDataUseCase: RefreshUserDataUseCase,
    private val deleteAppletUseCase : DeleteAppletUseCase
) : BaseActivityViewModel() {

    private val _hasSlack  = MutableLiveData<Boolean> ()
    val hasSlack: LiveData<Boolean> = _hasSlack

    val refresh = MutableLiveData(false)

    init {
        getUser()
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO){
            refreshUserDataUseCase()
            refresh.postValue(false)
        }
    }


    private fun getUser() {
        viewModelScope.launch(Dispatchers.IO){
            getLoggedUserUseCase().collect{user->
                user?.let {
                _hasSlack.postValue(!it.slack_access_token.isNullOrEmpty())
            }?:run {
                _hasSlack.postValue(false)
            }
            }
        }
    }

    suspend fun deleteApplet(id: Long): Boolean {
          return deleteAppletUseCase(id)
    }
}