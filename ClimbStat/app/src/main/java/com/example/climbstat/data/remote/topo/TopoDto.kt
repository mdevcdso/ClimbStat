package com.example.climbstat.data.remote.topo

import com.example.climbstat.domain.model.Topo
import com.google.gson.annotations.SerializedName

data class TopoDto(
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val idBoulder: String,
    val idUser: TopoUserDto,
    val isFlash: Boolean,
    val nbAttempts: Int,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int
)

fun TopoDto.toDomainModel(): Topo = Topo(
    id = this.id,
    attemptDate = this.createdAt,
    idBoulder = this.idBoulder,
    userName = this.idUser.name,
    isFlash = this.isFlash,
    nbAttempts = this.nbAttempts
)