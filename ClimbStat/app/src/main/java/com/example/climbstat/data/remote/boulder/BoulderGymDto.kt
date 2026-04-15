package com.example.climbstat.data.remote.boulder

import com.google.gson.annotations.SerializedName

data class BoulderGymDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val location: String,
    val franchise: String
)