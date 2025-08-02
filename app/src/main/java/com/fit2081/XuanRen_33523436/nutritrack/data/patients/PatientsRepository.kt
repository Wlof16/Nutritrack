package com.fit2081.XuanRen_33523436.nutritrack.data.patients

import android.content.Context
import com.fit2081.XuanRen_33523436.nutritrack.NutriCoachDatabase
import kotlinx.coroutines.flow.Flow

class PatientsRepository(context: Context) {
    private val patientDao =
        NutriCoachDatabase.getDatabase(context).patientDao()

    val allPatients: Flow<List<Patient>> = patientDao.getAllPatients()

    suspend fun insert(patient: Patient) {
        patientDao.insert(patient)
    }

    suspend fun update(patient: Patient) {
        patientDao.update(patient)
    }

    fun getPatientById(patientId: Int): Flow<Patient> {
        return patientDao.getPatientById(patientId)
    }

//    Used in LoginScreen
    fun getRegisteredPatientsId(): Flow<List<Int>> {
        return patientDao.getRegisteredPatientsId()
    }

//    Used in RegisterScreen
    fun getUnregisteredPatientsId(): Flow<List<Int>>  {
        return patientDao.getUnregisteredPatientsId()
    }

    fun getAverageHEIFAScore(): Flow<List<AverageHEIFA>>  {
        return patientDao.getAverageHEIFAScore()
    }

    suspend fun hasUserWithPass(patientId: Int, patientPass: String): Boolean {
        return patientDao.hasUserWithPass(patientId, patientPass)
    }

    suspend fun hasUserWithPhoneNum(patientId: Int, patientPhoneNum: String): Boolean {
        return patientDao.hasUserWithPhoneNum(patientId, patientPhoneNum)
    }

    suspend fun updatePasswordAndUsername(userId: Int, newPassword: String, newUsername: String) {
        return patientDao.updatePasswordAndUsername(userId, newPassword, newUsername)
    }

    suspend fun updatePassword(userId: Int, newPassword: String) {
        return patientDao.updatePassword(userId, newPassword)
    }

    suspend fun updateUsername(userId: Int, newUsername: String) {
        return patientDao.updateUsername(userId, newUsername)
    }
}