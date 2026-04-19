package com.example.climbstat.data.remote.climbingGym

import com.example.climbstat.Constants
import com.example.climbstat.domain.model.ClimbingGym
import com.google.gson.annotations.SerializedName

data class ClimbingGymDto(
    @SerializedName("_id")
    val id: String,
    val address: String,
    val clossingHours: String,
    val cotationType: String,
    val createdAt: String,
    val description: String,
    val franchise: String,
    val image: String,
    val location: String,
    val name: String,
    val openingHours: String,
    val tags: List<String>,
    val updatedAt: String
)

fun ClimbingGymDto.toDomainModel(): ClimbingGym = ClimbingGym(
    id = this.id,
    address = this.address,
    closingHours = this.clossingHours,
    cotationType = this.cotationType,
    createdAt = this.createdAt,
    description = this.description,
    franchise = this.franchise,
    image = this.image.replace("localhost", Constants.IMAGE_BASE_URL),
    location = this.location,
    name = this.name,
    openingHours = this.openingHours,
    tags = this.tags,
    updatedAt = this.updatedAt
)