package com.example.mercardolivre.ui.favoritos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mercardolivre.data.local.AppDatabase
import com.example.mercardolivre.data.local.Favoritos
import com.example.mercardolivre.data.repository.FavoritosRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class FavoritosUiState(
    val favoritos: List<Favoritos> = emptyList()
)

// 1. Herda de AndroidViewModel e remove repositório do construtor
class FavoritosViewModel(application: Application) : AndroidViewModel(application) {

    // 2. Instancia o repositório internamente
    private val repository: FavoritosRepository = FavoritosRepository(
        AppDatabase.getDatabase(application).favoritosDAO()
    )

    private val _uiState = MutableStateFlow(FavoritosUiState())
    val uiState: StateFlow<FavoritosUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.buscarTodos().collect { lista ->
                _uiState.update { it.copy(favoritos = lista) }
            }
        }
    }
}

// O arquivo FavoritosViewModelFactory.kt pode ser DELETADO