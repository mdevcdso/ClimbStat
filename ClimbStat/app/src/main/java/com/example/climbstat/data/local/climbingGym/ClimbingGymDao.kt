package com.example.climbstat.data.local.climbingGym

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import com.example.climbstat.domain.model.ClimbingGym
import com.example.climbstat.data.local.climbingGym.ClimbingGymEntity

@Dao
interface ClimbingGymDao {

    @Query("SELECT * FROM climbing_gym")
    fun getAllGyms(): Flow<List<ClimbingGymEntity>>

    @Query("SELECT * FROM climbing_gym WHERE id = :id")
    suspend fun getGymById(id: String): ClimbingGymEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGym(gym: ClimbingGymEntity)

    @Delete
    suspend fun deleteGym(gym: ClimbingGymEntity)

    @Query("DELETE FROM climbing_gym")
    suspend fun clearAll()
}