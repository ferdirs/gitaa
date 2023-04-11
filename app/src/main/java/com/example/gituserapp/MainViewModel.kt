package com.example.gituserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gituserapp.model.Search
import com.example.gituserapp.model.User
import com.example.gituserapp.network.ApiConfig
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel: ViewModel() {

    val _user = MutableLiveData<ArrayList<User>?>()
    val user: LiveData<ArrayList<User>?> = _user

    val _searchUser = MutableLiveData<ArrayList<User>?>()
    val searchUser : LiveData<ArrayList<User>?> = _searchUser

    val _loading = MutableLiveData<Boolean>()
    val loading : LiveData<Boolean> = _loading

    init {
        viewModelScope.launch{ getListUser() }
    }

    suspend fun getListUser() {
        coroutineScope {
            _loading.value = true
            val getUser = ApiConfig.getApiService().getListUsers()
            try {
                _user.postValue(getUser)
                _loading.value = false
            } catch (e: Exception) {
                _loading.value = false
                Log.e("ViewModelMain", "onFailure: ${e.message.toString()}")
            } }

    }

    fun getSearchUser(user: String) {
        _loading.value = true
        val api = ApiConfig.getApiService().getUserSearch(user)

        api.enqueue(object : Callback<Search>{
            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                if (response.isSuccessful){
                    val data = response.body()
                    if (data != null){
                        if (data.items != null){
                            _searchUser.postValue(data.items)
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                _loading.value = false
                Log.d("GAGAL", "onFailure: ${t.message.toString()}" )
            }

        })

    }

}