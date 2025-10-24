package com.example.mercardolivre.ui.perfil

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import com.example.mercardolivre.Opcao
import com.example.mercardolivre.listaPerfil
import com.example.mercardolivre.listaUsuarios


@Composable
fun PerfilScreen(onGoBack: () -> Unit) {
    Scaffold(
        topBar = { PerfilTopBar( onGoBack = onGoBack ) },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color(0xFFF0F0F0))
                .verticalScroll(rememberScrollState())
        ) {
            perfilBanner()
            opcoesDoPerfil()

        }
    }
}

@Composable
fun ItemDeOpcao(opcao: Opcao, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(Color.White) // Fundo branco para cada item
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = opcao.icone,
            contentDescription = opcao.opcaoNome,
            tint = Color.DarkGray,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(text = opcao.opcaoNome, fontSize = 18.sp, color = Color.Black)
            Text(text = opcao.opcaoDesc, fontSize = 16.sp, color = Color.Gray)
        }

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = "Ir para ${opcao.opcaoNome}",
            tint = Color.DarkGray,
            modifier = Modifier.size(24.dp)
        )
    }
    Divider(color = Color.LightGray.copy(alpha = 0.5f))
}

@Composable
fun opcoesDoPerfil(modifier: Modifier = Modifier){
    val opcoes = listaPerfil()


    Column(modifier = modifier) {
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color(0xFFF0F0F0))
        )

        opcoes.forEach { opcaoItem ->
            ItemDeOpcao(
                opcao = opcaoItem,
                onClick = {
                    TODO()
                }
            )
        }
    }
}

@Composable
fun perfilBanner() {
    val user = listaUsuarios().first()
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .background(Color(0xFFFFEB3B))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        )   {
            Box(modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ){
                Icon(
                    imageVector = user.icone,
                    contentDescription = "Foto do Perfil",
                    modifier = Modifier.size(48.dp),
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(16.dp))


            Text(
                text = user.nome,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Text(
                text = user.email,
                fontSize = 14.sp,
                color = Color.Black.copy(alpha = 0.7f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilTopBar(onGoBack: () -> Unit) {
    CenterAlignedTopAppBar(
        title = { Text("Meu Perfil") },
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


// --- Preview ---
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PerfilPreview() {
    MaterialTheme {
        PerfilScreen( onGoBack = {})
    }
}