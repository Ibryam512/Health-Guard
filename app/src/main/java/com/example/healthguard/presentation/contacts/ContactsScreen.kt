package com.example.healthguard.presentation.contacts

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.healthguard.presentation.contacts.components.AddContactDialog
import com.example.healthguard.presentation.contacts.components.ContactItem

@Composable
fun ContactsScreen(
    modifier: Modifier = Modifier,
    viewModel: ContactsViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val openAlertDialog = rememberSaveable() { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                when {
                    uiState.isLoading -> {
                        Text(
                            text = "Loading contacts...",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                    }
                    uiState.contacts.isNotEmpty() -> {
                        LazyColumn(
                            modifier = Modifier.weight(1f),
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            items(uiState.contacts) { contact ->
                                ContactItem(
                                    contact = contact,
                                    onDelete = {
                                        viewModel.deleteContact(contact)
                                    }
                                )
                            }
                        }
                    }
                    else -> {
                        Text(
                            text = "No contacts yet. Add a contact below.",
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(16.dp)
                        )
                    }
                }
                // We don't want to show the button when loading for better UX
                if (!uiState.isLoading) {
                    Button(
                        onClick = {
                            openAlertDialog.value = true
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = Color.White
                        ),
                    ) {
                        Text(
                            text = "Add contact"
                        )
                    }
                }
            }

            when {
                openAlertDialog.value -> {
                    AddContactDialog(
                        onAddContact = { contact ->
                            viewModel.addContact(contact)
                            openAlertDialog.value = false
                        },
                        onDismissRequest = {
                            openAlertDialog.value = false
                        }
                    )
                }
            }
        }
    }
}