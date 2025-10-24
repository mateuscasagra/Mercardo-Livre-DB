package com.example.mercardolivre.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercardolivre.Produto
import com.example.mercardolivre.data.local.AppDatabase
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

// 2. Herda de AndroidViewModel e remove repositórios do construtor
class HomeViewModel(application: Application) : AndroidViewModel(application) {

    // 3. Instancia os repositórios internamente
    private val produtoRepository: ProdutoRepository = ProdutoRepository()
    private val carrinhoRepository: CarrinhoRepository = CarrinhoRepository(
        AppDatabase.getDatabase(application).carrinhoDAO()
    )

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

    // 4. Funções que representam eventos da UI
    fun adicionarAoCarrinho(produto: Produto) {
        viewModelScope.launch {
            val item = Carrinho(nomeProduto = produto.titulo, valor = produto.preco)
            carrinhoRepository.inserir(item)
            // Não é necessário feedback de UI aqui, apenas inserimos
        }
    }
}

// O arquivo HomeViewModelFactory.kt pode ser DELETADO