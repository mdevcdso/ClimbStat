package com.example.climbstat.data.remote.boulder

import com.example.climbstat.domain.model.Boulder
import com.google.gson.annotations.SerializedName

data class BoulderDto(
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val description: String,
    val difficulty: String,
    val idGym: String,
    val image: String,
    val types: List<String>,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int,
)

fun BoulderDto.toDomainModel(): Boulder = Boulder(
    id = this.id,
    createdAt = this.createdAt,
    description = this.description,
    difficulty = this.difficulty,
    idGym = this.idGym,
    image = this.image,
    types = this.types
)