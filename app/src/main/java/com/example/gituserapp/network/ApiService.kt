package com.example.gituserapp.network

import com.example.gituserapp.model.User
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {

    @GET("users")
    @Headers( "Authorization: token $TOKEN_API")
    suspend fun getListUsers(): ArrayList<User>


    companion object{
        private const val TOKEN_API = "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"
    }

}