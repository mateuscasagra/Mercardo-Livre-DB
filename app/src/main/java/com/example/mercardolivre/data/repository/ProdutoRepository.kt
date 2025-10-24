package com.example.mercardolivre.data.repository

import com.example.mercardolivre.Produto
import com.example.mercardolivre.listaProdutos
import com.example.mercardolivre.produtosEmPromocao

class ProdutoRepository {

    fun getProdutos(): List<Produto> {
        return listaProdutos()
    }

    fun getProdutosEmPromocao(): List<Produto> {
        return produtosEmPromocao(listaProdutos())
    }
}