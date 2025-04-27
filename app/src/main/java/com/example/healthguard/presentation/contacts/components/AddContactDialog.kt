package com.example.healthguard.presentation.contacts.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.healthguard.data.contact.Contact

@Composable
fun AddContactDialog(
    onAddContact: (Contact) -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        AddContactCard(
            onAddContact = onAddContact
        )
    }

}
