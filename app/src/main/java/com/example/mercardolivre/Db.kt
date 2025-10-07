package com.empresa.bancoosorio

import android.content.Context
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
    val nomeProduto: String
)

@Dao
interface FavoritosDAO{

    @Insert
    suspend fun inserir(produto: String)

    @Query("SELECT * FROM Favoritos")
    suspend fun buscarTodos() : List<String>

    @Delete
    suspend fun deletar(produto: String)


}




@Database(entities = [Favoritos::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun filmesDAO(): FavoritosDAO

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
                    ).build()
                    INSTANCE = instance
                    return instance
                }

            }

        }


    }

}