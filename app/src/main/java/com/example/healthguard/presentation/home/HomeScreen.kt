package com.example.healthguard.presentation.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.healthguard.presentation.home.components.SOSButton

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        SOSButton(
            modifier = Modifier,
            onClick = onClick
        )
    }
}
