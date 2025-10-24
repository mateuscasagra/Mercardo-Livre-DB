package com.example.mercardolivre.ui.carrinho

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mercardolivre.data.local.Carrinho
import com.example.mercardolivre.data.repository.CarrinhoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// Classe de dados para o estado da UI [cite: 287, 367]
data class CarrinhoUiState(
    val itens: List<Carrinho> = emptyList(),
    val valorTotal: Double = 0.0
)

// ViewModel para a CarrinhoScreen [cite: 297]
class CarrinhoViewModel(private val repository: CarrinhoRepository) : ViewModel() {

    // _uiState é privada e mutável [cite: 387, 393]
    private val _uiState = MutableStateFlow(CarrinhoUiState())
    // uiState é pública e imutável (read-only) [cite: 391, 396]
    val uiState: StateFlow<CarrinhoUiState> = _uiState.asStateFlow()

    init {
        // Coleta o Flow do repositório [cite: 304]
        viewModelScope.launch {
            repository.buscarTodos().collect { lista ->
                // Atualiza o estado [cite: 306]
                _uiState.update {
                    it.copy(
                        itens = lista,
                        valorTotal = lista.sumOf { item -> item.valor }
                    )
                }
            }
        }
    }

    // Funções que representam eventos da UI [cite: 323]
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

// Factory para injetar o Repositório [cite: 407, 413]
class CarrinhoViewModelFactory(
    private val repository: CarrinhoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarrinhoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarrinhoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}