package com.example.climbstat.data.remote.topo

import com.google.gson.annotations.SerializedName

data class TopoUserDto(
    @SerializedName("_id")
    val id: String,
    val name: String
)