package com.example.healthguard.presentation.contacts

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.healthguard.presentation.contacts.components.AddContactDialog

@Composable
fun ContactsScreen(modifier: Modifier = Modifier) {
    val openAlertDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier
        .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = "Contacts Screen",
            style = MaterialTheme.typography.headlineLarge
        )
        Button(
            onClick = {
                openAlertDialog.value = true
            },
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ) {
            Text(text = "Add Contact")
        }
    }

    when {
        openAlertDialog.value -> {
            AddContactDialog(
                onAddContact = { contact ->
                    openAlertDialog.value = false
                },
                onDismissRequest = {
                    openAlertDialog.value = false
                }
            )
        }
    }
}