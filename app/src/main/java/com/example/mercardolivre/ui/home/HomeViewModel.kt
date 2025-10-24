package com.example.mercardolivre.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mercardolivre.Produto
import com.example.mercardolivre.data.local.Carrinho
import com.example.mercardolivre.data.repository.CarrinhoRepository
import com.example.mercardolivre.data.repository.ProdutoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// 1. Define o estado da UI
data class HomeUiState(
    val produtos: List<Produto> = emptyList()
)

// 2. Cria o ViewModel
class HomeViewModel(
    private val produtoRepository: ProdutoRepository,
    private val carrinhoRepository: CarrinhoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        // Carrega os produtos assim que o ViewModel é criado
        carregarProdutos()
    }

    private fun carregarProdutos() {
        // Busca os produtos do repositório
        _uiState.update {
            it.copy(produtos = produtoRepository.getProdutos())
        }
    }

    // 3. Funções que representam eventos da UI
    fun adicionarAoCarrinho(produto: Produto) {
        viewModelScope.launch {
            val item = Carrinho(nomeProduto = produto.titulo, valor = produto.preco)
            carrinhoRepository.inserir(item)
            // Não é necessário feedback de UI aqui, apenas inserimos
        }
    }
}

// 4. Factory para injetar os Repositórios
class HomeViewModelFactory(
    private val produtoRepo: ProdutoRepository,
    private val carrinhoRepo: CarrinhoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel(produtoRepo, carrinhoRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}