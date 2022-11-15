package com.navigation.latihan.githubuserapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navigation.latihan.githubuserapp.api.RetrofitClient
import com.navigation.latihan.githubuserapp.data.model.User
import com.navigation.latihan.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchModel : ViewModel() {

    val listUser = MutableLiveData<ArrayList<User>>()

    fun setSearch(query: String) {
        RetrofitClient.apiInstance
            .getSearchUser(query)
            .enqueue(object : Callback<UserResponse> {
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>
                ) {
                    if (response.isSuccessful) {
                        listUser.postValue(response.body()?.items)
                    } else {
                        Log.d(TAG, "onFailure: ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d(TAG, "onFailure; ${t.message.toString()}")
                }


            })
    }

    fun getSearch(): LiveData<ArrayList<User>> {
        return listUser
    }

    companion object {
        const val TAG = "SearchModel"
    }
}