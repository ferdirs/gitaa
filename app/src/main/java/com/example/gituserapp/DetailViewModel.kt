package com.example.gituserapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gituserapp.model.User
import com.example.gituserapp.network.ApiConfig
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class DetailViewModel(username: String):ViewModel() {

    private val _userDetail= MutableLiveData<User?>()
    val userDetail: LiveData<User?> = _userDetail

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        viewModelScope.launch {
            getUserDetail(username)
        }
    }

    private suspend fun getUserDetail(username: String){
        coroutineScope {
            _loading.value = true
            val getDetail = ApiConfig.getApiService().getDetailUser(username)
            try {
                _loading.value = false
                _userDetail.postValue(getDetail)
            }catch (e:Exception){
                _loading.value = false
                Log.d("GAGAL", "getUserDetail: ${e.message.toString()}")
            }
        }
    }

}