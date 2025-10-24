package com.example.mercardolivre.data.repository

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import com.example.mercardolivre.Produto

class ProdutoRepository {

    // Lógica movida de Data.kt para cá
    fun getProdutos(): List<Produto> {
        return mutableListOf(
            Produto(Icons.Default.Favorite, "Meia", 100.00, true, false),
            Produto(Icons.Default.Favorite, "Sapato", 100.00, true, false),
            Produto(Icons.Default.Favorite, "Cinto", 100.00, false, false),
            Produto(Icons.Default.Favorite, "Camisa", 100.00, true, false),
            Produto(Icons.Default.Favorite, "Blusa", 100.00, true, false),
            Produto(Icons.Default.Favorite, "Boné", 100.00, false, false)
        )
    }

    // Lógica movida de Data.kt para cá
    fun getProdutosEmPromocao(): List<Produto> {
        return getProdutos().filter { produto -> produto.promo }
    }
}