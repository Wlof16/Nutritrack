package com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipDao {
    @Insert
    suspend fun insert(nutriCoachTip: NutriCoachTip)

    @Query("SELECT tipMessage FROM nutricoach_tips WHERE patientId = :patientID")
    fun getAllTipsByID(patientID: Int): Flow<List<String>>
}