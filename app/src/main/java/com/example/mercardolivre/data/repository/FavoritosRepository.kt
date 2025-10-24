package com.example.mercardolivre.data.repository

import com.example.mercardolivre.data.local.Favoritos
import com.example.mercardolivre.data.local.FavoritosDAO
import kotlinx.coroutines.flow.Flow

class FavoritosRepository(private val favoritosDAO: FavoritosDAO) {

    fun buscarTodos(): Flow<List<Favoritos>> = favoritosDAO.buscarTodos()

    suspend fun inserir(produto: Favoritos) {
        favoritosDAO.inserir(produto)
    }

    suspend fun deletarPeloNome(nomeDoProduto: String) {
        favoritosDAO.deletarPeloNome(nomeDoProduto)
    }

    suspend fun buscarPeloNome(nomeDoProduto: String): Favoritos? {
        return favoritosDAO.buscarPeloNome(nomeDoProduto)
    }
}