package com.sms.pipe.view.activities.notifications

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel

class NotificationsViewModel : BaseFragmentViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text
}