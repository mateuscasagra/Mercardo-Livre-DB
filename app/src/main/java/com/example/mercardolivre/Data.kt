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

fun listaProdutos(): MutableList<Produto> {
    var listaProdutos = mutableListOf(
        Produto(Icons.Default.Favorite, "Meia", 100.00, true, false),
        Produto(Icons.Default.Favorite, "Sapato", 100.00, true, false),
        Produto(Icons.Default.Favorite, "Cinto", 100.00, false, false),
        Produto(Icons.Default.Favorite, "Camisa", 100.00, true, false),
        Produto(Icons.Default.Favorite, "Blusa", 100.00, true, false),
        Produto(Icons.Default.Favorite, "Boné", 100.00, false, false)
    )
    return listaProdutos
}

fun produtosEmPromocao(listaProdutos: List<Produto>) : List<Produto>{
    return listaProdutos.filter { produto -> produto.promo }.toMutableList()
}

fun listaUsuarios(): MutableList<User> {
    var listaUsuarios = mutableListOf(
        User(Icons.Default.Person, "Andre", "andre@gmail.com")
    )

    return listaUsuarios
}

fun listaPerfil(): MutableList<Opcao>{
    var listaPerfil = mutableListOf(
        Opcao(Icons.Default.List, "Suas Informações", "Nome de preferência e dados para te indentificar"),
        Opcao(Icons.Default.Info, "Dados da sua conta", "Dados que representam sua conta"),
        Opcao(Icons.Default.Lock, "Segurança", "Suas configurações de segurança"),
        Opcao(Icons.Default.Lock, "Privacidade", "Preferências e controle do uso dos seus dados"),
        Opcao(Icons.Default.LocationOn, "Endereços", "Endereços salvos na sua conta"),
        Opcao(Icons.Default.Email, "Comunicações", "Escolha o tipo de informação que você deseja receber")
    )
    return listaPerfil
}