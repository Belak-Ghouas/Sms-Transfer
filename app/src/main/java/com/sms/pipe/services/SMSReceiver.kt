package com.sms.pipe.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.util.Log
import com.sms.pipe.data.models.MessageModel
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.domain.usecases.OnSMSReceivedUseCase
import com.sms.pipe.utils.Result
import com.sms.pipe.utils.doIfSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class SMSReceiver : BroadcastReceiver() {

    private val tag = " SMSReceiver "
    private val initMessagingUseCase : InitMessagingUseCase by inject(InitMessagingUseCase::class.java)
    private val onSmsReceivedUseCase : OnSMSReceivedUseCase by inject(OnSMSReceivedUseCase::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            Log.d(tag,"On SMS received")
            intent?.let {
                if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION) {
                    extractMessage(intent).doIfSuccess { message ->
                        Log.e("SMSReceiver", it.toString())
                        CoroutineScope(Dispatchers.IO).launch {
                            onSmsReceivedUseCase(message)
                        }
                    }
                }
            } ?: kotlin.run {
                Log.e(tag, "the intent is null")
            }
        }
    }

    private fun extractMessage(intent: Intent): Result<MessageModel> {
        val messages = Telephony.Sms.Intents.getMessagesFromIntent(intent)
        if (messages.isNullOrEmpty()) {
            Log.e(tag, "No SMS messages found in intent")
            return Result.Failure(1000, "the smsBundle is null")
        }
        val messageModel = MessageModel()
        val smsBody = StringBuilder()
        for (message in messages) {
            messageModel.sender = message.displayOriginatingAddress ?: ""
            smsBody.append(message.messageBody)
        }
        messageModel.messageBody = smsBody.toString()
        Log.e(tag, messageModel.toString())
        return Result.Success(messageModel)
    }
}
