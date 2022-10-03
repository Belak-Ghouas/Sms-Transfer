package com.sms.pipe.view.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sms.pipe.view.base.BaseFragmentViewModel

class NotificationsViewModel : BaseFragmentViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}