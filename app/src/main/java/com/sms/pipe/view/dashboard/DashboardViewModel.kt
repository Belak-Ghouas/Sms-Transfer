package com.sms.pipe.view.dashboard


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.RefreshUserDataUseCase
import com.sms.pipe.view.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DashboardViewModel(private val refreshUserDataUseCase: RefreshUserDataUseCase) : BaseFragmentViewModel() {


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

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO){
            refreshUserDataUseCase()
            getUser()
        }
    }
}