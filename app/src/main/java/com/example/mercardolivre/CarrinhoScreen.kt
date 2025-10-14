package com.example.mercardolivre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarrinhoScreen(onGoBack: () -> Unit) {

    var listaDeItens by remember { mutableStateOf<List<Carrinho>>(emptyList()) }
    val context = LocalContext.current
    val carrinhoDAO = remember { AppDatabase.getDatabase(context).carrinhoDAO() }
    val scope = rememberCoroutineScope()

    val valorTotal = remember(listaDeItens) {
        listaDeItens.sumOf { it.valor }
    }

    fun removerItem(item: Carrinho) {
        scope.launch {
            carrinhoDAO.deletarPeloNome(item.nomeProduto)
            // Atualiza a lista na UI após a remoção
            listaDeItens = carrinhoDAO.buscarTodos()
        }
    }

    fun finalizarCompra(){
        scope.launch {
            carrinhoDAO.deletarTudo()
            listaDeItens = emptyList()
        }
    }

    LaunchedEffect(Unit) {
        listaDeItens = carrinhoDAO.buscarTodos()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Meu Carrinho") },
                navigationIcon = {
                    IconButton(onClick = onGoBack) {
                        Icon(Icons.Default.ArrowBack, "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFFFEB3B)
                )
            )
        },
        bottomBar = {
            if (listaDeItens.isNotEmpty()) {
                TotalSection(
                    valorTotal = valorTotal,
                    onFinalizarCompra = { finalizarCompra() }
                )
            }
        }
    ) { paddingValues ->

        if (listaDeItens.isEmpty()) {
            CarrinhoVazio(paddingValues)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Color(0xFFF0F0F0))
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(listaDeItens) { item ->
                    CarrinhoItemCard(
                        item = item,
                        onRemoveClick = { removerItem(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun CarrinhoItemCard(item: Carrinho, onRemoveClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = item.nomeProduto, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(
                    text = "R$ ${String.format("%.2f", item.valor)}",
                    color = Color.Gray,
                    fontSize = 14.sp
                )
            }
            // Botão para deletar o item
            IconButton(onClick = onRemoveClick) {
                Icon(Icons.Default.Delete, "Remover Item", tint = Color.Gray)
            }
        }
    }
}

@Composable
fun TotalSection(valorTotal: Double, onFinalizarCompra: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total:", fontSize = 20.sp, fontWeight = FontWeight.Light)
                Text(
                    "R$ ${String.format("%.2f", valorTotal)}",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onFinalizarCompra,
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0033A0)) // Azul do tema
            ) {
                Text("Finalizar Compra", fontSize = 18.sp, color = Color.White)
            }
        }
    }
}

@Composable
fun CarrinhoVazio(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                Icons.Default.ShoppingCart,
                "Carrinho Vazio",
                modifier = Modifier.size(80.dp),
                tint = Color.LightGray
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Seu carrinho está vazio",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray
            )
            Text(
                "Adicione produtos para vê-los aqui.",
                color = Color.Gray,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CarrinhoPreview() {
    MaterialTheme {
        CarrinhoScreen(onGoBack = {})
    }
}