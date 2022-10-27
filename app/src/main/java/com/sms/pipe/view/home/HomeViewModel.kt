package com.sms.pipe.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.domain.usecases.GetAppletsUseCase
import com.sms.pipe.view.base.BaseFragmentViewModel
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAppletsUseCase: GetAppletsUseCase
) : BaseFragmentViewModel() {

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


}