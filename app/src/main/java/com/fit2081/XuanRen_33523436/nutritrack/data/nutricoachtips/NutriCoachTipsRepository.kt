package com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips

import android.content.Context
import com.fit2081.XuanRen_33523436.nutritrack.NutriCoachDatabase
import kotlinx.coroutines.flow.Flow

class NutriCoachTipsRepository(context: Context) {
    private val nutriCoachTipDao =
        NutriCoachDatabase.getDatabase(context).nutriCoachTipDao()

    fun getAllTipsByID(patientID: Int): Flow<List<String>> {
        return nutriCoachTipDao.getAllTipsByID(patientID)
    }

    suspend fun insert(nutriCoachTip: NutriCoachTip) {
        nutriCoachTipDao.insert(nutriCoachTip)
    }
}