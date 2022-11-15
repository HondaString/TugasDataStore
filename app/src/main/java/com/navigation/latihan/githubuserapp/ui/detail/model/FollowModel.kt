package com.navigation.latihan.githubuserapp.ui.detail.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.navigation.latihan.githubuserapp.api.RetrofitClient
import com.navigation.latihan.githubuserapp.data.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowModel : ViewModel(){

    val followers = MutableLiveData<ArrayList<User>>()

    val following = MutableLiveData<ArrayList<User>>()


    fun setFollowing(username: String){
        RetrofitClient.apiInstance
            .getFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if(response.isSuccessful){
                        following.postValue(response.body())
                    }

                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }
            })
    }

    fun getFollowing() : LiveData<ArrayList<User>> {
        return following
    }

    fun setFollower(username: String){
        RetrofitClient.apiInstance
            .getFollower(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful){
                        followers.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.e("Failure", t.message.toString())
                }

            })
    }

    fun getFollower() : LiveData<ArrayList<User>> {
        return followers
    }

}