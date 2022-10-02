package com.sms.pipe.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage
import android.util.Log
import com.sms.pipe.data.models.MessageModel
import com.sms.pipe.domain.usecases.InitMessagingUseCase
import com.sms.pipe.domain.usecases.SendMessageUseCase
import com.sms.pipe.utils.Result
import com.sms.pipe.utils.doIfSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class SMSReceiver : BroadcastReceiver() {

    private val tag = " SMSReceiver "
    private val sendMessageUseCase :SendMessageUseCase by inject(SendMessageUseCase::class.java)
    private val initMessagingUseCase : InitMessagingUseCase by inject(InitMessagingUseCase::class.java)

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.IO).launch {
            if(initMessagingUseCase()){
            intent?.let {
                if (intent.action == Telephony.Sms.Intents.SMS_RECEIVED_ACTION ) {
                    val serviceIntent = Intent(context, PipeService::class.java)
                    // displayMessage(intent)
                    extractMessage(intent).doIfSuccess {message->
                        Log.e("SMSReceiver",it.toString())
                        if(message.sender=="STELLANTIS"){
                            CoroutineScope(Dispatchers.IO).launch {
                                sendMessageUseCase(message.messageBody,"#test-android-app")
                            }
                        }else{
                            Log.e("SMSReceiver","ignore sms from other senders")
                        }

                    }
                    context?.startService(serviceIntent)
                }
            } ?: kotlin.run {
                Log.e(tag, "the intent is null")
            }
            }
        }

    }

    private fun displayMessage(intent: Intent) {
        val smsBundle = intent.extras
        var smsBody: String=""
        smsBundle?.let {
            val format = smsBundle.getString("format")
            //---retrieve the SMS message received---
            val pdus =smsBundle.get("pdus") as Array<Any>
            val messages = arrayOfNulls<SmsMessage>(pdus.size)
            for (i in messages.indices) {
                messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)
                smsBody =  messages[i]?.displayOriginatingAddress?:""
                smsBody += messages[i]?.messageBody

                Log.e(tag,smsBody)
            }
        }
    }


    private fun extractMessage (intent: Intent): Result<MessageModel> {
        val smsBundle = intent.extras
        val messageModel = MessageModel()
        var smsBody = ""
        smsBundle?.let {
            val format = smsBundle.getString("format")
            //---retrieve the SMS message received---
            // pdu is protocol description unit
            val pdus =smsBundle.get("pdus") as Array<*>
            val messages = arrayOfNulls<SmsMessage>(pdus.size)
            for (i in messages.indices) {
                messages[i] = SmsMessage.createFromPdu(pdus[i] as ByteArray, format)
                messageModel.sender =  messages[i]?.displayOriginatingAddress?:""
                smsBody += messages[i]!!.messageBody
            }
            messageModel.messageBody = smsBody
        }?: kotlin.run {
            Log.e(tag,"smsBundle is null  : $smsBundle")
            return Result.Failure(1000 ,"the smsBundle is null")
        }
        Log.e(tag, messageModel.toString())
        return Result.Success(messageModel)
    }

}