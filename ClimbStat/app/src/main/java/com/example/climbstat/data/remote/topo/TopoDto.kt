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
    val v: Int,
    val comment: String?
)

fun TopoDto.toDomainModel(): Topo = Topo(
    id = this.id,
    attemptDate = this.createdAt,
    idBoulder = this.idBoulder,
    boulderDifficulty = null,
    userId = this.idUser.id,
    userName = this.idUser.name,
    isFlash = this.isFlash,
    nbAttempts = this.nbAttempts,
    comment = this.comment ?: ""
)

data class TopoBoulderDto(
    @SerializedName("_id") val id: String,
    val difficulty: String,
    val types: List<String>,
    val description: String,
    val image: String,
    val videoBeta: String?
)

data class UserTopoDto(
    @SerializedName("_id")
    val id: String,
    val createdAt: String,
    val idBoulder: TopoBoulderDto,
    val idUser: TopoUserDto,
    val isFlash: Boolean,
    val nbAttempts: Int,
    val updatedAt: String,
    @SerializedName("__v")
    val v: Int,
    val comment: String?
)

fun UserTopoDto.toDomainModel(): Topo = Topo(
    id = this.id,
    attemptDate = this.createdAt,
    idBoulder = this.idBoulder.id,
    boulderDifficulty = this.idBoulder.difficulty,
    userId = this.idUser.id,
    userName = this.idUser.name,
    isFlash = this.isFlash,
    nbAttempts = this.nbAttempts,
    comment = this.comment ?: ""
)
