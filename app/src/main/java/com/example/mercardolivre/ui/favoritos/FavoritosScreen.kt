package com.example.mercardolivre.ui.favoritos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mercardolivre.data.local.Favoritos
import androidx.compose.foundation.lazy.items
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoritosScreen(
    onGoBack: () -> Unit,
    // Instancia o ViewModel SEM factory
    viewModel: FavoritosViewModel = viewModel()
) {
    // Coleta o estado do ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // LaunchedEffect e o DAO local foram removidos

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meus Favoritos") },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB3B), // Cor amarela
                )
            )
        },
    ) { paddingValues ->

        // Lê o estado do uiState
        if (uiState.favoritos.isEmpty()) {
            EmptyStateMessage(paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF0F0F0))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Lê a lista do uiState
                items(uiState.favoritos) { favorito ->
                    FavoritoItemCard(favorito = favorito)
                }
            }
        }
    }
}

// ... (O restante do arquivo não muda) ...
@Composable
fun FavoritoItemCard(favorito: Favoritos) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Favorite,
                contentDescription = "Ícone de favorito",
                tint = Color.Red,
                modifier = Modifier.size(40.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = favorito.nomeProduto,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "R$ ${String.format("%.2f", favorito.valor)}",
                    color = Color.Gray,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun EmptyStateMessage(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Aviso",
                modifier = Modifier.size(60.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Nenhum favorito encontrado",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Gray
            )
            Text(
                text = "Adicione produtos aos favoritos para vê-los aqui.",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FavoritosPreview() {
    MaterialTheme {
        FavoritosScreen(onGoBack = {})
    }
}