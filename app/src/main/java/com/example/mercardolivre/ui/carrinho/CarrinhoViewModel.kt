package com.example.mercardolivre.ui.carrinho

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mercardolivre.data.local.AppDatabase
import com.example.mercardolivre.data.local.Carrinho
import com.example.mercardolivre.data.repository.CarrinhoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Classe de dados para o estado da UI
data class CarrinhoUiState(
    val itens: List<Carrinho> = emptyList(),
    val valorTotal: Double = 0.0
)

// 1. Herda de AndroidViewModel e remove repositório do construtor
class CarrinhoViewModel(application: Application) : AndroidViewModel(application) {

    // 2. Instancia o repositório internamente
    private val repository: CarrinhoRepository = CarrinhoRepository(
        AppDatabase.getDatabase(application).carrinhoDAO()
    )

    private val _uiState = MutableStateFlow(CarrinhoUiState())
    val uiState: StateFlow<CarrinhoUiState> = _uiState.asStateFlow()

    init {
        // Coleta o Flow do repositório
        viewModelScope.launch {
            repository.buscarTodos().collect { lista ->
                // Atualiza o estado
                _uiState.update {
                    it.copy(
                        itens = lista,
                        valorTotal = lista.sumOf { item -> item.valor }
                    )
                }
            }
        }
    }

    // Funções que representam eventos da UI
    fun removerItem(item: Carrinho) {
        viewModelScope.launch {
            repository.deletarPeloNome(item.nomeProduto)
            // O Flow atualizará o uiState automaticamente
        }
    }

    fun finalizarCompra() {
        viewModelScope.launch {
            repository.deletarTudo()
            // O Flow atualizará o uiState automaticamente
        }
    }
}

// O arquivo CarrinhoViewModelFactory.kt pode ser DELETADO