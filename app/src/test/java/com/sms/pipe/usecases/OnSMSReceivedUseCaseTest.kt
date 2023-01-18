package com.sms.pipe.usecases

import com.sms.pipe.data.models.MessageModel
import com.sms.pipe.domain.repositories.AppletRepository
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.domain.repositories.UserRepository
import com.sms.pipe.domain.usecases.OnSMSReceivedUseCase
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Test

class OnSMSReceivedUseCaseTest {


    private val classUnderTest: OnSMSReceivedUseCase


    @MockK
    private lateinit var messagingRepository: MessagingRepository

    @MockK
    private lateinit var appletRepository: AppletRepository

    @MockK
    private lateinit var userRepository: UserRepository


    init {
        MockKAnnotations.init(this)
        classUnderTest = OnSMSReceivedUseCase(messagingRepository, appletRepository, userRepository)
    }

    @Test
    fun `test on Sms Received`() = runTest {
        val sms = MessageModel("body","sender")
        classUnderTest.invoke(sms)
    }

}