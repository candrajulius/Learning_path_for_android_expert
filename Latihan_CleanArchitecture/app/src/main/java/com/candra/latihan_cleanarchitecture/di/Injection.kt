package com.candra.latihan_cleanarchitecture.di

import com.candra.latihan_cleanarchitecture.data.IMessageDataSource
import com.candra.latihan_cleanarchitecture.data.MessageDataSource
import com.candra.latihan_cleanarchitecture.data.MessageRepository
import com.candra.latihan_cleanarchitecture.domain.IMessageRepository
import com.candra.latihan_cleanarchitecture.domain.MessageInteractor
import com.candra.latihan_cleanarchitecture.domain.MessageUseCase

object Injection
{
    fun provideUseCase(): MessageUseCase{
        val messageRepository = provideRepository()
        return MessageInteractor(messageRepository = messageRepository)
    }

    private fun provideRepository(): IMessageRepository{
        val messageDataSource = provideDataSource()
        return MessageRepository(messageDataSource)
    }

    private fun provideDataSource(): IMessageDataSource{
        return MessageDataSource()
    }
}