package com.navigation.latihan.githubuserapp.ui.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addToFavorite(Favorite: Favorite)

    @Query("SELECT * FROM FAVORITE")
    fun getFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) FROM FAVORITE WHERE Favorite.id = :id")
    suspend fun checkUser(id: Int): Int

    @Query("DELETE FROM FAVORITE WHERE Favorite.id = :id")
    suspend fun removeFromFavorite(id: Int): Int
}