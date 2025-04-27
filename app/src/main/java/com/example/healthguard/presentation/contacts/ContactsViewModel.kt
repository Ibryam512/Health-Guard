package com.example.healthguard.presentation.contacts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.healthguard.data.contact.Contact
import com.example.healthguard.service.ContactService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactsViewModel @Inject constructor(
    private val contactService: ContactService
) : ViewModel() {

    private val _uiState = MutableStateFlow(ContactsUiState())
    val uiState: StateFlow<ContactsUiState> = _uiState.asStateFlow()

    init {
        loadContacts()
    }

    private fun loadContacts() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            val contacts = contactService.getAllContacts()
            _uiState.value = ContactsUiState(
                isLoading = false,
                contacts = contacts
            )
        }
    }

    fun addContact(contact: Contact) {
        viewModelScope.launch {
            contactService.insertContact(contact)
            loadContacts()
        }
    }

    fun deleteContact(contact: Contact) {
        viewModelScope.launch {
            contactService.deleteContact(contact.id)
            loadContacts()
        }
    }
}