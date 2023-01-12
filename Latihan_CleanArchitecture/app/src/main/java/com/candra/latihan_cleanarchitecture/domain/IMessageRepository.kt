package com.candra.latihan_cleanarchitecture.domain

interface IMessageRepository
{
    fun getWelcomeMessage(name: String): MessageEntity
}