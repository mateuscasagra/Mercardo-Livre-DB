package com.example.mercardolivre.data.repository

import com.example.mercardolivre.data.local.Carrinho
import com.example.mercardolivre.data.local.CarrinhoDAO
import kotlinx.coroutines.flow.Flow

// O Repositório abstrai o DAO
class CarrinhoRepository(private val carrinhoDAO: CarrinhoDAO) {

    fun buscarTodos(): Flow<List<Carrinho>> = carrinhoDAO.buscarTodos()

    suspend fun inserir(produto: Carrinho) {
        carrinhoDAO.inserir(produto)
    }

    suspend fun deletarPeloNome(nomeDoProduto: String) {
        carrinhoDAO.deletarPeloNome(nomeDoProduto)
    }

    suspend fun deletarTudo() {
        carrinhoDAO.deletarTudo()
    }
}