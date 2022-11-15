package com.navigation.latihan.githubuserapp.ui.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Favorite::class], version = 1
)
abstract class DatabaseCRUD: RoomDatabase() {
    companion object{
        var INSTANCE : DatabaseCRUD? = null

        fun getDatabase(context: Context): DatabaseCRUD? {
            if (INSTANCE==null){
                synchronized(DatabaseCRUD::class){ INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseCRUD::class.java,
                    "Database"
                ).build()
                }
            }
            return INSTANCE
        }
    }

    abstract fun FavoriteDao(): FavoriteDao
}