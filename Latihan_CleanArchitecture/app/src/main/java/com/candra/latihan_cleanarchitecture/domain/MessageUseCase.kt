package com.candra.latihan_cleanarchitecture.domain

interface MessageUseCase {
    fun getMessage(name: String): MessageEntity
}