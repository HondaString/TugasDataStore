package com.navigation.latihan.githubuserapp.api

import com.navigation.latihan.githubuserapp.BuildConfig
import com.navigation.latihan.githubuserapp.data.model.DetaiUserlResponse
import com.navigation.latihan.githubuserapp.data.model.User
import com.navigation.latihan.githubuserapp.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("search/users")
    fun getSearchUser(
        @Query("q") query: String
    ): Call<UserResponse>

    @GET("users/{username}")
    fun getDetail (
        @Path("username") username : String
    ): Call<DetaiUserlResponse>

    @GET("users/{username}/followers")
    fun getFollower (
        @Path("username") username : String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    fun getFollowing (
        @Path("username") username : String
    ): Call<ArrayList<User>>


}