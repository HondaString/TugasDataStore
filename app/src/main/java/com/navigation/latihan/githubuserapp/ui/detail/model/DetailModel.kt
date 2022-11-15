package com.navigation.latihan.githubuserapp.ui.detail.model

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.navigation.latihan.githubuserapp.api.RetrofitClient
import com.navigation.latihan.githubuserapp.data.model.DetaiUserlResponse
import com.navigation.latihan.githubuserapp.ui.local.DatabaseCRUD
import com.navigation.latihan.githubuserapp.ui.local.DatabaseCRUD.Companion.getDatabase
import com.navigation.latihan.githubuserapp.ui.local.Favorite
import com.navigation.latihan.githubuserapp.ui.local.FavoriteDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailModel(application: Application) : AndroidViewModel(application) {

    val userDetail = MutableLiveData<DetaiUserlResponse>()

    private var FavoriteDao: FavoriteDao?
    private var dbCRUD: DatabaseCRUD? = getDatabase(application)

    init {
        FavoriteDao = dbCRUD?.FavoriteDao()
    }

    fun setDetail(username: String) {
        RetrofitClient.apiInstance
            .getDetail(username)
            .enqueue(object : Callback<DetaiUserlResponse> {
                override fun onResponse(
                    call: Call<DetaiUserlResponse>,
                    response: Response<DetaiUserlResponse>
                ) {
                    if (response.isSuccessful) {
                        userDetail.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetaiUserlResponse>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun getDetail(): LiveData<DetaiUserlResponse> {
        return userDetail
    }

    fun addToFavorite(username: String, id: Int, avatarUrl: String?){
        CoroutineScope(Dispatchers.IO).launch {
            val user = Favorite(
                username,
                id,
                avatarUrl
            )
            FavoriteDao?.addToFavorite(user)
        }
    }

    suspend fun checkUser(id: Int) = FavoriteDao?.checkUser(id)

    fun removeFromFavorite(id: Int){
        CoroutineScope(Dispatchers.IO).launch {
            FavoriteDao?.removeFromFavorite(id)
        }
    }
}