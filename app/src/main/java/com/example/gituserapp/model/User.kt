package com.example.gituserapp.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int = 0,
    @field:SerializedName("name")
    val name: String?,
    @field:SerializedName("login")
    val login: String?,
    @field:SerializedName("avatar_url")
    val avatarUrl: String?,
):Parcelable
