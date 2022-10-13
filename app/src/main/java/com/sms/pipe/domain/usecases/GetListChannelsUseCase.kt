package com.sms.pipe.domain.usecases

import com.sms.pipe.data.models.ChannelModel
import com.sms.pipe.domain.repositories.MessagingRepository
import com.sms.pipe.utils.doIfSuccess

class GetListChannelsUseCase(private val messagingRepository: MessagingRepository) {

    suspend operator fun invoke(): List<ChannelModel> {
        messagingRepository.getListChannels().doIfSuccess {
         return it
      }
        return emptyList()
    }
}