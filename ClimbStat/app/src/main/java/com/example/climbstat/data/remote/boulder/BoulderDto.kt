package com.example.climbstat.data.remote.boulder

import android.icu.util.LocaleData
import com.example.climbstat.Constants
import com.example.climbstat.data.remote.climbingGym.ClimbingGymDto
import com.example.climbstat.domain.model.Boulder
import com.example.climbstat.domain.model.ClimbingGym
import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.time.LocalDate

data class BoulderDto(
    @SerializedName("_id")
    val id: String,
    val description: String,
    val difficulty: String,
    val idGym: BoulderGymDto,
    val image: String,
    val types: List<String>,
    val createdAt: String?,
    val updatedAt: String?,
    @SerializedName("__v")
    val v: Int,
)

fun BoulderDto.toDomainModel(): Boulder = Boulder(
    id = this.id,
    createdAt = this.createdAt ?: LocalDate.now().toString(),
    description = this.description,
    difficulty = this.difficulty,
    idGym = idGym.id,
    image = this.image.replace("localhost", Constants.IMAGE_BASE_URL),
    types = this.types
)