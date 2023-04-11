package com.example.gituserapp

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.core.widget.ContentLoadingProgressBar
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gituserapp.databinding.ActivityMainBinding
import com.example.gituserapp.model.User
import com.example.gituserapp.utils.Constant
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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setViewModel()
        loadingAnim()
        searchView()

    }


    private fun setViewModel(){
        mainViewModel.user.observe(this, {
            if (it != null) {
                adapter.addDataToList(it)
                setRV()
                Log.d("coba", "setViewModel: $it")
            }
        })
        mainViewModel._searchUser.observe(this,{
            if (it != null){
                adapter.addDataToList(it)
                binding.rvMainpage.visibility = View.VISIBLE
            }

        })
    }

    private fun setRV(){
        with(binding){
            val layoutManager = GridLayoutManager(this@MainActivity, 2, GridLayoutManager.VERTICAL, false)
            binding.rvMainpage.layoutManager = layoutManager
            binding.rvMainpage.setHasFixedSize(true)
            binding.rvMainpage.adapter = adapter
            adapter.setOnItemClickCallback(object : SetOnClickCallback{
                override fun onItemClicked(user: User) {
                    val intent = Intent(applicationContext, DetailActivity::class.java)
                    intent.putExtra(Constant.USER_KEY, user)
                    intent.putExtra(Constant.USERNAME_KEY, user)
                    intent.putExtra(Constant.ID_KEY, user)
                    startActivity(intent)
                }
            })
        }
    }

    private fun loadingAnim(){
        mainViewModel.loading.observe(this,{
            showProgressBar(it)
        })
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.loadingImg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun searchView(){
        with(binding){
            searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    showProgressBar(true)
                    if (query != null) {
                        mainViewModel.getSearchUser(query)
                    }
                    mainViewModel.searchUser.observe(this@MainActivity){
                        if (it != null){
                            adapter.addDataToList(it)
                            searchView.clearFocus()
                            showProgressBar(false)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
        }
    }


}