package com.example.climbstat.data.local.climbingGym

import com.example.climbstat.domain.model.ClimbingGym
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun ClimbingGymEntity.toDomain(): ClimbingGym {
    val gson = Gson()
    val type = object : TypeToken<List<String>>() {}.type

    return ClimbingGym(
        id = id,
        address = address,
        closingHours = this@toDomain.closingHours,
        cotationType = cotationType,
        createdAt = createdAt,
        description = description,
        franchise = franchise,
        image = image,
        location = location,
        name = name,
        openingHours = openingHours,
        tags = gson.fromJson(tags, type),
        updatedAt = updatedAt
    )
}

fun ClimbingGym.toEntity(): ClimbingGymEntity {
    return ClimbingGymEntity(
        id = id,
        address = address,
        closingHours = closingHours,
        cotationType = cotationType,
        createdAt = createdAt,
        description = description,
        franchise = franchise,
        image = image,
        location = location,
        name = name,
        openingHours = openingHours,
        tags = Gson().toJson(tags),
        updatedAt = updatedAt
    )
}