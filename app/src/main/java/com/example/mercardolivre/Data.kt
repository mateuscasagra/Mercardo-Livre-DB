package com.example.mercardolivre

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val rota: String
)


data class Produto(
    val icone: ImageVector,
    val titulo: String,
    val preco: Double,
    val promo: Boolean,
    val fav: Boolean
)

data class User(
    val icone: ImageVector,
    val nome: String,
    val email: String
)

data class Opcao(
    val icone: ImageVector,
    val opcaoNome: String,
    val opcaoDesc: String
)

sealed class Screen(val rota: String){
    object Home: Screen("tela_inicial")
    object Promos: Screen("tela_promos")
    object Perfil: Screen("tela_perfil")
    object Favoritos: Screen("tela_favoritos")
    object Carrinho: Screen("tela_carrinho")
}

// As funções listaProdutos(), produtosEmPromocao(),
// listaUsuarios() e listaPerfil() FORAM REMOVIDAS DAQUI
// e movidas para seus respectivos Repositórios.