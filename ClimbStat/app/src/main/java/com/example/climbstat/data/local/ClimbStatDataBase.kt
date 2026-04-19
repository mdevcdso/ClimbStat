package com.example.climbstat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.climbstat.data.local.boulder.BoulderDao
import com.example.climbstat.data.local.boulder.BoulderEntity
import com.example.climbstat.data.local.climbingGym.ClimbingGymDao
import com.example.climbstat.data.local.climbingGym.ClimbingGymEntity
import com.example.climbstat.data.local.topo.TopoDao
import com.example.climbstat.data.local.topo.TopoEntity

@Database(
    entities = [
        ClimbingGymEntity::class,
        BoulderEntity::class,
        TopoEntity::class
   ],
    version = 3,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun climbingGymDao(): ClimbingGymDao
    abstract fun boulderDao(): BoulderDao
    abstract fun topoDao(): TopoDao
}