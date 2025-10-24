package com.example.mercardolivre.ui.promos

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.mercardolivre.Produto
import com.example.mercardolivre.data.local.AppDatabase
import com.example.mercardolivre.data.local.Carrinho
import com.example.mercardolivre.data.local.Favoritos
import com.example.mercardolivre.data.repository.CarrinhoRepository
import com.example.mercardolivre.data.repository.FavoritosRepository
import com.example.mercardolivre.data.repository.ProdutoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class PromosUiState(
    val produtosEmPromocao: List<Produto> = emptyList(),
    val favoritosNomes: Set<String> = emptySet() // Usar um Set de nomes para checagem rápida
)

// 1. Herda de AndroidViewModel e remove repositórios do construtor
class PromosViewModel(application: Application) : AndroidViewModel(application) {

    // 2. Instancia os repositórios internamente
    private val produtoRepository: ProdutoRepository = ProdutoRepository()
    private val favoritosRepository: FavoritosRepository = FavoritosRepository(
        AppDatabase.getDatabase(application).favoritosDAO()
    )
    private val carrinhoRepository: CarrinhoRepository = CarrinhoRepository(
        AppDatabase.getDatabase(application).carrinhoDAO()
    )

    private val _uiState = MutableStateFlow(PromosUiState())
    val uiState: StateFlow<PromosUiState> = _uiState.asStateFlow()

    init {
        carregarProdutos()
        observarFavoritos()
    }

    private fun carregarProdutos() {
        _uiState.update {
            it.copy(produtosEmPromocao = produtoRepository.getProdutosEmPromocao())
        }
    }

    private fun observarFavoritos() {
        viewModelScope.launch {
            favoritosRepository.buscarTodos().collect { listaFavoritos ->
                _uiState.update {
                    it.copy(favoritosNomes = listaFavoritos.map { fav -> fav.nomeProduto }.toSet())
                }
            }
        }
    }

    fun onFavoritoClick(produto: Produto, isFavorito: Boolean) {
        viewModelScope.launch {
            if (isFavorito) {
                // Se já é favorito, remove
                favoritosRepository.deletarPeloNome(produto.titulo)
            } else {
                // Se não é, adiciona
                val favorito = Favoritos(nomeProduto = produto.titulo, valor = produto.preco)
                favoritosRepository.inserir(favorito)
            }
            // O Flow em observarFavoritos() atualizará o estado automaticamente
        }
    }

    fun adicionarAoCarrinho(produto: Produto) {
        viewModelScope.launch {
            val item = Carrinho(nomeProduto = produto.titulo, valor = produto.preco)
            carrinhoRepository.inserir(item)
            // Não precisamos de feedback visual imediato nesta tela
        }
    }
}

// O arquivo PromosViewModelFactory.kt pode ser DELETADO