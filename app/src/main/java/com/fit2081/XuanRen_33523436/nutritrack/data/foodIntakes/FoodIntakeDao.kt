package com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodIntakeDao {
    @Query("SELECT * FROM food_intakes WHERE patientId = :patientID")
    fun getFoodIntakeById(patientID: Int): Flow<FoodIntake?>

    @Upsert
    suspend fun upsertFoodIntake(foodIntake: FoodIntake)
}