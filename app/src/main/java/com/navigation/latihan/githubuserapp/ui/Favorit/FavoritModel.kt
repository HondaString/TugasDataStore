package com.navigation.latihan.githubuserapp.ui.Favorit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.navigation.latihan.githubuserapp.ui.local.DatabaseCRUD
import com.navigation.latihan.githubuserapp.ui.local.Favorite
import com.navigation.latihan.githubuserapp.ui.local.FavoriteDao

class FavoritModel(application: Application): AndroidViewModel(application) {

    private var FavoriteDao: FavoriteDao?
    private var dbCRUD: DatabaseCRUD? = DatabaseCRUD.getDatabase(application)

    init {
        FavoriteDao = dbCRUD?.FavoriteDao()
    }

    fun getFavoriteUser(): LiveData<List<Favorite>>? {
        return FavoriteDao?.getFavorite()
    }

}