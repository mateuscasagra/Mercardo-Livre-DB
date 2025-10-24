package com.example.mercardolivre.ui.favoritos

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
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

class FavoritosViewModel(private val repository: FavoritosRepository) : ViewModel() {

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

class FavoritosViewModelFactory(
    private val repository: FavoritosRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoritosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavoritosViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}