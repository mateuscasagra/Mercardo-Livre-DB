package com.example.mercardolivre

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Imports do ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mercardolivre.data.local.AppDatabase
import com.example.mercardolivre.data.repository.CarrinhoRepository
import com.example.mercardolivre.data.repository.ProdutoRepository
import com.example.mercardolivre.ui.home.HomeViewModel
import com.example.mercardolivre.ui.home.HomeViewModelFactory
import com.example.mercardolivre.ui.theme.MercardoLivreTheme
import kotlin.math.min


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onGoToPromos: () -> Unit,
    // 1. Instancia o ViewModel usando a Factory
    viewModel: HomeViewModel = viewModel(
        factory = HomeViewModelFactory(
            produtoRepo = ProdutoRepository(),
            carrinhoRepo = CarrinhoRepository(AppDatabase.getDatabase(LocalContext.current).carrinhoDAO())
        )
    )
) {

    // 2. Coleta o estado do ViewModel
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var textPesquisa by remember { mutableStateOf("") }


    Column (
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(Color.White)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color(0xFFFFEB3B),
                titleContentColor = Color.Black
            ),
            title = {
                TextField(
                    value = textPesquisa,
                    onValueChange = { novoTexto -> textPesquisa = novoTexto },
                    placeholder = { Text("Buscar no Mercado Livre") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        //.height(50.dp)
                        .clip(RoundedCornerShape(percent = 50)),
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Ícone de busca") },
                    colors = TextFieldDefaults.colors(
                        // Cores do Conteúdo
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.Gray,
                        cursorColor = Color(0xFF00A650),
                        // Cores do Container
                        focusedContainerColor = Color(0xFFF5F5F5),
                        unfocusedContainerColor = Color(0xFFF5F5F5),
                        disabledContainerColor = Color.LightGray,
                        // "Cores" para remover a linha indicadora
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent
                    )
                )
            }
        )
        BannerPromocional( onGoToPromos = onGoToPromos )
        SecaoIcons()
        // 3. Passa o estado (produtos) e os eventos (clicks) para o Composable
        SecaoProdutos(
            produtos = uiState.produtos,
            onCarrinhoClick = { produto ->
                viewModel.adicionarAoCarrinho(produto)
            }
        )
    }
}

// ... (BannerPromocional e SecaoIcons não mudam) ...
@Composable
fun BannerPromocional( onGoToPromos: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { onGoToPromos() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Blue),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("LIQUIDAÇÃO PARA COMPRAR AGORA",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp)
        }
    }
}

@Composable
fun SecaoIcons(){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Icone(icone = Icons.Default.Home, texto = "Entregar")
        Icone(icone = Icons.Default.Check, texto = "Ofertas")
        Icone(icone = Icons.Default.ThumbUp, texto = "Cupons")
        Icone(icone = Icons.Default.Phone, texto = "Celulares")
        Icone(icone = Icons.Default.AddCircle, texto = "Moda")
    }
}

@Composable
fun Icone(icone: ImageVector, texto: String){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .background(Color.LightGray),
            contentAlignment = Alignment.Center
        ) {
            Icon(icone, contentDescription = texto, modifier = Modifier.size(30.dp), tint = Color.Black)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(texto, fontSize = 12.sp, textAlign = TextAlign.Center)
    }
}

// 4. SecaoProdutos agora recebe a lista e o evento
@Composable
fun SecaoProdutos(
    produtos: List<Produto>,
    onCarrinhoClick: (Produto) -> Unit
){
    // Removemos a lógica de busca do DAO daqui
    // val produtos = listaProdutos()
    // val context = LocalContext.current
    // val carrinhoDAO = remember { AppDatabase.getDatabase(context).carrinhoDAO() }

    Column(modifier = Modifier.padding(horizontal = 16.dp)){
        Text("Também te interessa", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        for(i in produtos.indices step 3) {
            Row(modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)){
                for(j in i until min(i + 3, produtos.size)){
                    val produto = produtos[j]
                    CardProduto(
                        produto = produto,
                        // 5. Passa o evento de clique para o CardProduto
                        onCarrinhoClick = { onCarrinhoClick(produto) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// 6. CardProduto agora recebe um lambda, não o DAO
@Composable
fun CardProduto(
    produto: Produto,
    onCarrinhoClick: () -> Unit,
    modifier: Modifier = Modifier
){
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .height(80.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = produto.icone,
                    contentDescription = produto.titulo,
                    modifier = Modifier.size(72.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            Box(
                modifier = Modifier.height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = produto.titulo,
                    fontSize = 15.sp,
                    maxLines = 2,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(4.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "R$ ${produto.preco}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                // 7. O botaoCarrinho agora só recebe o evento de clique
                botaoCarrinho(onClick = onCarrinhoClick)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MercardoLivreTheme {
        HomeScreen (onGoToPromos = {})
    }
}