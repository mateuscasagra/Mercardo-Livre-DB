package com.example.mercardolivre

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mercardolivre.data.local.AppDatabase
import com.example.mercardolivre.data.repository.CarrinhoRepository
import com.example.mercardolivre.data.repository.FavoritosRepository
import com.example.mercardolivre.data.repository.ProdutoRepository
import com.example.mercardolivre.ui.promos.PromosViewModel
import com.example.mercardolivre.ui.promos.PromosViewModelFactory


@Composable
fun PromosScreen(
    onGoBack: () -> Unit,
    // Instancia o ViewModel complexo com múltiplas dependências [cite: 426]
    viewModel: PromosViewModel = viewModel(
        factory = PromosViewModelFactory(
            produtoRepo = ProdutoRepository(),
            favoritosRepo = FavoritosRepository(AppDatabase.getDatabase(LocalContext.current).favoritosDAO()),
            carrinhoRepo = CarrinhoRepository(AppDatabase.getDatabase(LocalContext.current).carrinhoDAO())
        )
    )
) {
    // Coleta o estado
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = { TopBar(onGoBack = onGoBack) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0))
        ) {
            BannerPrincipal()
            IconRow()
            OfertasSection(
                modifier = Modifier.weight(1f),
                produtos = uiState.produtosEmPromocao, // Passa a lista do uiState
                favoritosNomes = uiState.favoritosNomes, // Passa os favoritos do uiState
                onFavoritoClick = { produto, isFavorito -> // Delega evento
                    viewModel.onFavoritoClick(produto, isFavorito)
                },
                onCarrinhoClick = { produto -> // Delega evento
                    viewModel.adicionarAoCarrinho(produto)
                }
            )
        }
    }
}

// ... (TopBar, BannerPrincipal, IconRow, ShortcutIcon não mudam) ...

@Composable
fun OfertasSection(
    modifier: Modifier = Modifier,
    produtos: List<Produto>,
    favoritosNomes: Set<String>,
    onFavoritoClick: (Produto, Boolean) -> Unit,
    onCarrinhoClick: (Produto) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Text(
            text = "OFERTAS PARA COMPRAR AGORA",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))

        ProdutoCardGrid(
            modifier = Modifier.fillMaxHeight(),
            produtos = produtos,
            favoritosNomes = favoritosNomes,
            onFavoritoClick = onFavoritoClick,
            onCarrinhoClick = onCarrinhoClick
        )
    }
}

@Composable
fun ProdutoCardGrid(
    modifier: Modifier = Modifier,
    produtos: List<Produto>,
    favoritosNomes: Set<String>,
    onFavoritoClick: (Produto, Boolean) -> Unit,
    onCarrinhoClick: (Produto) -> Unit
) {
    // Os DAOs e o context foram removidos daqui
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(produtos) { produto ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(Color.LightGray),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Imagem do produto",
                            modifier = Modifier.size(50.dp),
                            tint = Color.DarkGray
                        )
                    }
                    Column(modifier = Modifier.padding(8.dp)) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = produto.titulo,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            // BotaoFavorito agora é controlado pelo estado
                            val isFavorito = favoritosNomes.contains(produto.titulo)
                            BotaoFavorito(
                                isFavorito = isFavorito,
                                onClick = { onFavoritoClick(produto, isFavorito) }
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "R$ ${produto.preco}",
                                color = Color(0xFF0033A0),
                                fontWeight = FontWeight.SemiBold
                            )
                            // botaoCarrinho agora é controlado pelo estado
                            botaoCarrinho(
                                onClick = { onCarrinhoClick(produto) }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun BotaoFavorito(
    isFavorito: Boolean,
    onClick: () -> Unit
) {
    // A lógica de estado e coroutine foi removida
    // Este Composable agora é "burro" [cite: 128]
    IconButton(onClick = onClick) {
        Icon(
            imageVector = if (isFavorito) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = if (isFavorito) "Remover dos favoritos" else "Adicionar aos favoritos",
            modifier = Modifier.size(25.dp),
            tint = if (isFavorito) Color.Red else Color.DarkGray
        )
    }
}

@Composable
fun botaoCarrinho(
    onClick: () -> Unit
) {
    // A lógica de estado e coroutine foi removida
    IconButton(onClick = onClick) {
        Icon(Icons.Default.ShoppingCart, contentDescription = "Carrinho")
    }
}

// --- Preview ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        PromosScreen(onGoBack = {})
    }
}

// (TopBar, BannerPrincipal, IconRow, ShortcutIcon composables não precisam de alteração)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(onGoBack: () -> Unit) {
    TopAppBar(
        title = { Text("Festival de Grandes Marcas") },
        navigationIcon = {
            IconButton(onClick = onGoBack) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Voltar")
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFFFEB3B), // Cor amarela
            titleContentColor = Color.Black,
            navigationIconContentColor = Color.Black
        )
    )
}


@Composable
fun BannerPrincipal() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFFFEB3B))
            .padding(16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column {
            Text(
                text = "FESTIVAL DE",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = "GRANDES MARCAS",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "ATÉ 60% OFF",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF0033A0) // Azul escuro
            )
        }
    }
}


@Composable
fun IconRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFFFEB3B))
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icon(
            imageVector = Icons.Default.ShoppingCart,
            contentDescription = "Imagem do produto",
            modifier = Modifier.size(50.dp),
            tint = Color.DarkGray
        )
    }
}


@Composable
fun ShortcutIcon(icon: ImageVector, text: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Color(0xFF0033A0)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
    }
}