package com.example.mercardolivre.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import com.example.mercardolivre.Opcao
import com.example.mercardolivre.User

class PerfilRepository {

    // Lógica movida de Data.kt para cá
    fun getUsuario(): User {
        return User(Icons.Default.Person, "Andre", "andre@gmail.com")
    }

    // Lógica movida de Data.kt para cá
    fun getOpcoesPerfil(): List<Opcao> {
        return mutableListOf(
            Opcao(Icons.Default.List, "Suas Informações", "Nome de preferência e dados para te indentificar"),
            Opcao(Icons.Default.Info, "Dados da sua conta", "Dados que representam sua conta"),
            Opcao(Icons.Default.Lock, "Segurança", "Suas configurações de segurança"),
            Opcao(Icons.Default.Lock, "Privacidade", "Preferências e controle do uso dos seus dados"),
            Opcao(Icons.Default.LocationOn, "Endereços", "Endereços salvos na sua conta"),
            Opcao(Icons.Default.Email, "Comunicações", "Escolha o tipo de informação que você deseja receber")
        )
    }
}