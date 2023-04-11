package com.example.gituserapp

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.gituserapp.databinding.UserRowBinding
import com.example.gituserapp.model.User
import kotlin.math.log

class AdapterUser:
    RecyclerView.Adapter<AdapterUser.UserViewHolder>() {
    private val listUser = ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[position]) }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addDataToList(items: ArrayList<User>) {
        listUser.clear()
        listUser.addAll(items)
        Log.d("coba2", "addDataToList: $items")
        notifyDataSetChanged()
    }

    override fun getItemCount() = listUser.size

    class UserViewHolder(private var binding: UserRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.name.text = user.login
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .into(binding.userImage)

        }
    }

    private lateinit var onItemClickCallback: SetOnClickCallback

    fun setOnItemClickCallback(onItemClickCallback: SetOnClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

}