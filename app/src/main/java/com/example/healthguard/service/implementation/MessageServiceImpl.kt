package com.example.healthguard.service.implementation

import com.example.healthguard.data.message.Message
import com.example.healthguard.data.message.MessagesRepository
import com.example.healthguard.service.MessageService
import kotlinx.coroutines.flow.first

class MessageServiceImpl(private val messagesRepository: MessagesRepository) : MessageService {
    override suspend fun getMessage(): Message {
        return messagesRepository.getLastMessage().first()
    }

    override suspend fun insertMessage(message: Message) {
        messagesRepository.insert(message)
    }

}