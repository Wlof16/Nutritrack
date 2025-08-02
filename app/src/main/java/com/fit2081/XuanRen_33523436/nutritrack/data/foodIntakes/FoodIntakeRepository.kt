package com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes

import android.content.Context
import com.fit2081.XuanRen_33523436.nutritrack.AuthManager
import com.fit2081.XuanRen_33523436.nutritrack.NutriCoachDatabase
import kotlinx.coroutines.flow.Flow

class FoodIntakeRepository(context: Context) {
    private val foodIntakeDao =
        NutriCoachDatabase.getDatabase(context).foodIntakeDao()

    fun getFoodIntakeById(patientID: Int): Flow<FoodIntake?> {
        return foodIntakeDao.getFoodIntakeById(patientID)
    }

    suspend fun upsertFoodIntake(foodIntake: FoodIntake) {
        foodIntakeDao.upsertFoodIntake(foodIntake)
    }
}