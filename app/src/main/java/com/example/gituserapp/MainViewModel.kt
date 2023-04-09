package com.example.gituserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gituserapp.model.User
import com.example.gituserapp.network.ApiConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    val _user = MutableLiveData<ArrayList<User>?>()
    val user: LiveData<ArrayList<User>?> = _user

    val userSearch = MutableLiveData<ArrayList<User>?>()
    val isLoading = MutableLiveData<Boolean>()

    init {
        viewModelScope.launch{ getListUser() }
    }

    suspend fun getListUser() {
        coroutineScope {
            val getUser = ApiConfig.getApiService().getListUsers()
            try {
                _user.postValue(getUser)
            } catch (e: Exception) {
                Log.e("ViewModelMain", "onFailure: ${e.message.toString()}")
            } }

    }

}