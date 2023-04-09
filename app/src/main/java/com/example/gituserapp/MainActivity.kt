package com.example.gituserapp

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gituserapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var binding: ActivityMainBinding
    private val adapter: AdapterUser by lazy {
        AdapterUser()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        mainViewModel.user.observe(this, {UserResponse ->
            if (UserResponse != null) {
                adapter.addDataToList(UserResponse)
                setRV()
                Log.d("coba", "setViewModel: $UserResponse")
            }
        })
        setRV()
        setViewModel()

    }


    private fun setViewModel(){
        ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        mainViewModel.user.observe(this, {UserResponse ->
            if (UserResponse != null) {
                adapter.addDataToList(UserResponse)
                setRV()
                Log.d("coba", "setViewModel: $UserResponse")
            }
        })
    }

    private fun setRV(){
        with(binding){
            val layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            rvMainpage.layoutManager = layoutManager
            rvMainpage.setHasFixedSize(true)
            rvMainpage.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    private fun loadingAnim(){
        mainViewModel.isLoading.observe(this,{
            showProgressBar(it)
        })
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.loadingImg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}