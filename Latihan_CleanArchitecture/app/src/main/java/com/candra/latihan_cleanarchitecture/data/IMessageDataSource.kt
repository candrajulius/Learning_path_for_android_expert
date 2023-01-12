package com.candra.latihan_cleanarchitecture.data

import com.candra.latihan_cleanarchitecture.domain.MessageEntity

interface IMessageDataSource
{
    fun getMessageSource(name: String): MessageEntity
}