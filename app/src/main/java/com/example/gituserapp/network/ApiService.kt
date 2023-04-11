package com.example.gituserapp.network

import com.example.gituserapp.model.Search
import com.example.gituserapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("users")
    @Headers( "Authorization: token $TOKEN_API")
    suspend fun getListUsers(): ArrayList<User>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN_API")
    fun getUserSearch(@Query("q") username: String): Call<Search>

    @GET("users/{username}")
    @Headers( "Authorization: token $TOKEN_API")
    suspend fun getDetailUser(@Path("username") username: String): User

    companion object{
        private const val TOKEN_API = "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"
    }

}