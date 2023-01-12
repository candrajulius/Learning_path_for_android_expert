package com.candra.latihan_cleanarchitecture.data

import com.candra.latihan_cleanarchitecture.domain.IMessageRepository
import com.candra.latihan_cleanarchitecture.domain.MessageEntity

class MessageRepository(
  private val messageDataSource: IMessageDataSource
): IMessageRepository
{
    override fun getWelcomeMessage(name: String): MessageEntity {
        return messageDataSource.getMessageSource(name)
    }
}