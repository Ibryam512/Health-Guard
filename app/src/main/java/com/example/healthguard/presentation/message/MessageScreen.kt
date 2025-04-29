package com.example.healthguard.presentation.message

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun MessageScreen(
    modifier: Modifier = Modifier,
    viewModel: MessageViewModel
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val messageText = uiState.value.message?.messageText ?: ""
    val isEditMode = uiState.value.isEditMode
    val charCount = messageText.length

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = messageText,
            onValueChange = {
                if (isEditMode) {
                    viewModel.updateMessageText(it)
                }
            },
            label = { Text("Message") },
            enabled = isEditMode,
            modifier = Modifier
                .width(300.dp)
                .height(200.dp),
            singleLine = false,
            maxLines = 10,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                disabledContainerColor = MaterialTheme.colorScheme.surface
            )
        )

        Text(
            text = "$charCount / 500 characters",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(top = 4.dp)
        )
        Text(
            text = "Tip: You can include medical details like allergies, blood type, or chronic conditions.",
            style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.Start)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Button(
                onClick = {
                    val currentMessage = uiState.value.message
                    if (isEditMode && currentMessage != null) {
                        viewModel.saveMessage(currentMessage)
                    } else if (currentMessage != null) {
                        viewModel.enterEditMode(currentMessage)
                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = Color.White
                ),
            ) {
                Text(if (isEditMode) "Save" else "Edit")
            }

            if (isEditMode) {
                OutlinedButton(
                    onClick = { viewModel.loadMessage() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
