package com.example.mercardolivre


import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import com.example.mercardolivre.Produto

@Entity(tableName = "Favoritos")
data class Favoritos(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nomeProduto: String,
    val valor: Double
)

@Dao
interface FavoritosDAO{

    @Insert
    suspend fun inserir(produto: Favoritos)

    @Query("SELECT * FROM Favoritos")
    suspend fun buscarTodos() : List<Favoritos>

    @Delete
    suspend fun deletar(produto: Favoritos)

    @Query("DELETE FROM Favoritos WHERE nomeProduto = :nomeDoProduto")
    suspend fun deletarPeloNome(nomeDoProduto: String)

    @Query("SELECT * FROM Favoritos WHERE nomeProduto = :nomeDoProduto LIMIT 1")
    suspend fun buscarPeloNome(nomeDoProduto: String): Favoritos?

}

@Entity(tableName = "Carrinho")
data class Carrinho(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val nomeProduto: String,
    val valor: Double
)

@Dao
interface CarrinhoDAO{

    @Insert
    suspend fun inserir(produto: Carrinho)

    @Query("SELECT * FROM Carrinho")
    suspend fun buscarTodos() : List<Carrinho>

    @Delete
    suspend fun deletar(produto: Carrinho)

    @Query("DELETE FROM Carrinho WHERE nomeProduto = :nomeDoProduto")
    suspend fun deletarPeloNome(nomeDoProduto: String)

    @Query("SELECT * FROM Carrinho WHERE nomeProduto = :nomeDoProduto LIMIT 1")
    suspend fun buscarPeloNome(nomeDoProduto: String): Carrinho?

    @Query("DELETE FROM Carrinho")
    suspend fun deletarTudo()



}



@Database(entities = [Favoritos::class, Carrinho::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun favoritosDAO(): FavoritosDAO
    abstract fun carrinhoDAO(): CarrinhoDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{

            val tempInstance = INSTANCE

            if(tempInstance != null){
                return tempInstance
            }else{

                synchronized(this){
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                    return instance
                }

            }

        }


    }

}