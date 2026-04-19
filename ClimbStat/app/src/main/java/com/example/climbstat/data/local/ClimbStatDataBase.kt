package com.example.climbstat.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.climbstat.data.local.climbingGym.ClimbingGymDao
import com.example.climbstat.data.local.climbingGym.ClimbingGymEntity

@Database(
    entities = [ClimbingGymEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun climbingGymDao(): ClimbingGymDao
}