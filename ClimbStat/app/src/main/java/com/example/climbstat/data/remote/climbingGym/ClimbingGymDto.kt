package com.example.climbstat.data.remote.climbingGym

import com.example.climbstat.Constants
import com.example.climbstat.domain.model.ClimbingGym
import com.google.gson.annotations.SerializedName

data class ClimbingGymDto(
    @SerializedName("_id")
    val id: String,
    val cotationType: String,
    val createdAt: String,
    val franchise: String,
    val image: String,
    val location: String,
    val name: String,
    val openingHours: String,
    val updatedAt: String
)

fun ClimbingGymDto.toDomainModel(): ClimbingGym = ClimbingGym(
    id = this.id,
    cotationType = this.cotationType,
    createdAt = this.createdAt,
    franchise = this.franchise,
    image = this.image.replace("localhost", Constants.IMAGE_BASE_URL),
    location = this.location,
    name = this.name,
    openingHours = this.openingHours,
    updatedAt = this.updatedAt
)