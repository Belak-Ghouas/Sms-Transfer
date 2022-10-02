package com.sms.pipe.view.activities.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sms.pipe.view.viewmodels.BaseFragmentViewModel

class DashboardViewModel : BaseFragmentViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}