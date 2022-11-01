package com.sms.pipe.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.*
import com.sms.pipe.view.base.BaseActivityViewModel
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val refreshUserDataUseCase: RefreshUserDataUseCase,
    private val deleteAppletUseCase : DeleteAppletUseCase,
    private val getAppletsUseCase: GetAppletsUseCase,
    private val getUserToken : GetUserTokenUseCase
) : BaseActivityViewModel() {

    private val _hasSlack  = MutableLiveData<Boolean> ()
    val hasSlack: LiveData<Boolean> = _hasSlack

    val refresh = MutableLiveData(false)

    private val _appletUi = MutableLiveData<AppletUi?>()
    val appletUi: LiveData<AppletUi?> = _appletUi

    fun getApplet() {
        viewModelScope.launch(Dispatchers.Default) {
            getAppletsUseCase().collect { list ->
                list.firstOrNull().let {
                    _appletUi.postValue(it)
                }
            }
        }
    }
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

    fun getToken(): String {
       return getUserToken()
    }
}