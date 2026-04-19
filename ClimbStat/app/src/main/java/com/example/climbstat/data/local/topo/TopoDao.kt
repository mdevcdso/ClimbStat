package com.example.climbstat.data.local.topo

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TopoDao {

    @Query("SELECT * FROM topo")
    fun getAllTopos(): List<TopoEntity>

    @Query("SELECT * FROM topo WHERE id = :id")
    suspend fun getTopoById(id: String): TopoEntity?

    @Query("SELECT * FROM topo WHERE userId = :userId")
    suspend fun getTopoByUserId(userId: String): List<TopoEntity>

    @Query("SELECT * FROM topo WHERE idBoulder = :boulderId")
    suspend fun getTopoByBoulderId(boulderId: String): List<TopoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTopo(topo: TopoEntity)

    @Query("DELETE FROM topo")
    suspend fun clearAll()
}