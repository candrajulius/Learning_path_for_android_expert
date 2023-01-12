package com.candra.latihan_cleanarchitecture.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.candra.latihan_cleanarchitecture.domain.MessageEntity
import com.candra.latihan_cleanarchitecture.domain.MessageUseCase

class MainViewModel(
    private val messageUseCase: MessageUseCase
): ViewModel()
{
    private val _message = MutableLiveData<MessageEntity>()

    val message get() = _message

    fun setName(name: String){
        _message.value = messageUseCase.getMessage(name)
    }
}