package com.example.healthguard.presentation.message

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthguard.data.message.Message
import com.example.healthguard.service.MessageService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageViewModel @Inject constructor(
    private val messageService: MessageService
): ViewModel() {
    private val _uiState = MutableStateFlow(MessageUiState())
    val uiState: StateFlow<MessageUiState> = _uiState.asStateFlow()

    init {
        loadMessage()
    }

    fun loadMessage() {
        viewModelScope.launch {
            val message = messageService.getMessage()
            _uiState.value = MessageUiState(
                message = message,
                isEditMode = false
            )
        }
    }

    fun saveMessage(message: Message) {
        viewModelScope.launch {
            messageService.insertMessage(message)
            loadMessage()
        }
    }

    fun enterEditMode(message: Message) {
        _uiState.value = MessageUiState(
            message = message,
            isEditMode = true
        )
    }

    fun updateMessageText(newText: String) {
        val current = _uiState.value.message
        if (current != null) {
            _uiState.value = _uiState.value.copy(
                message = current.copy(messageText = newText)
            )
        }
    }
}