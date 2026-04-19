package com.example.climbstat.data.local.boulder

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface BoulderDao {

    @Query("SELECT * FROM boulder")
    fun getAllBoulders(): List<BoulderEntity>

    @Query("SELECT * FROM boulder WHERE id = :id")
    suspend fun getBoulderById(id: String): BoulderEntity?

    @Query("SELECT * FROM boulder WHERE idGym = :idGym")
    suspend fun getBouldersByGymId(idGym: String): List<BoulderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBoulder(boulder: BoulderEntity)

    @Query("DELETE FROM boulder")
    suspend fun clearAll()
}