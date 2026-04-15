package com.example.climbstat.data.remote.topo

import com.google.gson.annotations.SerializedName

data class AddTopoResponse(
    @SerializedName("__v")
    val v: Int,
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val idBoulder: String,
    val idUser: String,
    val isFlash: Boolean,
    val nbAttempts: Int,
    val updatedAt: String
)