package com.sms.pipe.view.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.data.models.UserModel
import com.sms.pipe.domain.usecases.DeleteAccountUseCase
import com.sms.pipe.domain.usecases.GetLoggedUserUseCase
import com.sms.pipe.utils.doIfSuccess
import com.sms.pipe.view.base.BaseFragmentViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getLoggedUserUseCase: GetLoggedUserUseCase,
    private val deleteAccountUseCase: DeleteAccountUseCase
) : BaseFragmentViewModel() {

    private val _isUserDeleted = MutableLiveData<Boolean>()
    val isUserDeleted: LiveData<Boolean> = _isUserDeleted

    val user: LiveData<UserModel> = liveData(Dispatchers.IO) {
        getLoggedUserUseCase().collect { user ->
            user?.let {
                emit(it)
            }
        }
    }


    fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteAccountUseCase().doIfSuccess {
                _loggedOut.postValue(true)
                return@launch
            }
            _isUserDeleted.postValue(false)
        }
    }
}