package com.example.healthguard.data.message

import kotlinx.coroutines.flow.Flow

class MessagesRepositoryImpl(private val messageDao: MessageDao) : MessagesRepository {
    override suspend fun insert(message: Message) {
        messageDao.insertMessage(message)
    }

    override fun getLastMessage(): Flow<Message> = messageDao.getMessage()
}