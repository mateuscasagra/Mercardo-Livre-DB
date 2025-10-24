package com.example.mercardolivre.ui.perfil

import androidx.lifecycle.ViewModel
import com.example.mercardolivre.Opcao
import com.example.mercardolivre.User
import com.example.mercardolivre.data.repository.PerfilRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// 1. Define o estado da UI para o Perfil
data class PerfilUiState(
    val usuario: User? = null,
    val opcoes: List<Opcao> = emptyList()
)

// 2. Cria o ViewModel
class PerfilViewModel : ViewModel() {

    // 3. Instancia o repositório
    private val repository: PerfilRepository = PerfilRepository()

    private val _uiState = MutableStateFlow(PerfilUiState())
    val uiState: StateFlow<PerfilUiState> = _uiState.asStateFlow()

    init {
        // 4. Carrega os dados do repositório
        carregarDadosPerfil()
    }

    private fun carregarDadosPerfil() {
        _uiState.update {
            it.copy(
                usuario = repository.getUsuario(),
                opcoes = repository.getOpcoesPerfil()
            )
        }
    }
}