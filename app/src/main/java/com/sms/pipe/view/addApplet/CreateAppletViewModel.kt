package com.sms.pipe.view.addApplet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.domain.usecases.GetListChannelsUseCase
import com.sms.pipe.domain.usecases.StoreAppletUseCase
import com.sms.pipe.view.base.BaseActivityViewModel
import com.sms.pipe.view.model.AppletUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateAppletViewModel(private val getListChannelsUseCase : GetListChannelsUseCase , private val storeAppletUseCase : StoreAppletUseCase) : BaseActivityViewModel() {
    var newApplet: AppletUi? = null

    val channels : LiveData<List<ChannelModel>> =  liveData(Dispatchers.IO) {
       emit(getListChannelsUseCase())
    }

    private val _appletStored = MutableLiveData<Boolean>()
    val appletStored: LiveData<Boolean> = _appletStored


    fun storeApplet() {
        newApplet?.let {
            viewModelScope.launch(Dispatchers.IO){
                _appletStored.postValue(storeAppletUseCase(it))
            }

        }?: run {
            Log.e("CreateAppletViewModel", "The applet is null")
        }
    }

}