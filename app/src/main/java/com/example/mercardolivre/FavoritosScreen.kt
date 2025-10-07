package com.example.mercardolivre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview



@Composable
fun FavoritosScreen(onGoBack: () -> Unit) {
    Scaffold(
        topBar = { TopBar( onGoBack = onGoBack) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0))
        ) {
            BannerPrincipal()
            IconRow()
        }
    }
}

// --- Preview ---

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoritosPreview() {
    MaterialTheme {
        PerfilScreen( onGoBack = {})
    }
}