package com.example.healthguard.presentation.contacts

import com.example.healthguard.data.contact.Contact

data class ContactsUiState(
    val contacts: List<Contact> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
