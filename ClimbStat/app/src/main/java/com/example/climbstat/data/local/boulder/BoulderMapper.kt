package com.example.climbstat.data.local.boulder

import com.example.climbstat.domain.model.Boulder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

fun BoulderEntity.toDomain(): Boulder {
    val type = object : TypeToken<List<String>>() {}.type

    return Boulder(
        id = id,
        description = description,
        difficulty = difficulty,
        idGym = idGym,
        image = image,
        types = Gson().fromJson(types, type),
        createdAt = createdAt
    )
}

fun Boulder.toEntity(): BoulderEntity {
    return BoulderEntity(
        id = id,
        description = description,
        difficulty = difficulty,
        idGym = idGym,
        image = image,
        types = Gson().toJson(types),
        createdAt = createdAt
    )
}