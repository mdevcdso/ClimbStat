package com.example.climbstat.data.local.topo

import com.example.climbstat.domain.model.Topo

fun TopoEntity.toDomain(): Topo {
    return Topo(
        id = id,
        userId = userId,
        userName = userName,
        attemptDate = attemptDate,
        idBoulder = idBoulder,
        boulderDifficulty = boulderDifficulty,
        boulderGymId = boulderGymId,
        isFlash = isFlash,
        nbAttempts = nbAttempts,
        comment = comment
    )
}

fun Topo.toEntity(): TopoEntity {
    return TopoEntity(
        id = id,
        userId = userId,
        userName = userName,
        attemptDate = attemptDate,
        idBoulder = idBoulder,
        boulderDifficulty = boulderDifficulty,
        boulderGymId = boulderGymId,
        isFlash = isFlash,
        nbAttempts = nbAttempts,
        comment = comment
    )
}