package com.example.gituserapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.bumptech.glide.Glide
import com.example.gituserapp.databinding.ActivityDetailBinding
import com.example.gituserapp.model.User
import com.example.gituserapp.utils.Constant

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val detailUserName =intent.getStringExtra(Constant.USERNAME_KEY)

    }

    private fun setData(username:String){

        val user =intent.getParcelableExtra<User>(Constant.USER_KEY)

        val viewModelDetail:DetailViewModel by viewModels{
            DetailViewModelFactory(username)
        }
        viewModelDetail.loading.observe(this,{
            showProgressBar(it)
        })

        viewModelDetail.userDetail.observe(this, {
            if (it != null){

            }
        })
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.pg.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDataDetail(user: User?){
        if (user != null){
            binding.apply {
                Glide.with(root)
                    .load(user.avatarUrl)
                    .circleCrop()
                    .into(binding.detailUserImg)
                namaUserDetail.text = user.name

            }
        }
    }

}