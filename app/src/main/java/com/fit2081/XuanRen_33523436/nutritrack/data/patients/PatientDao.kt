package com.fit2081.XuanRen_33523436.nutritrack.data.patients

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {
    @Insert
    suspend fun insert(patient: Patient)

    @Query("SELECT * FROM patients")
    fun getAllPatients(): Flow<List<Patient>>

    @Update
    suspend fun update(patient: Patient)

    @Query("SELECT * FROM patients WHERE userID = :patientId")
    fun getPatientById(patientId: Int): Flow<Patient>

    @Query("SELECT userID FROM patients WHERE userPass IS NOT NULL")
    fun getRegisteredPatientsId(): Flow<List<Int>>

    @Query("SELECT userID FROM patients WHERE userPass IS NULL")
    fun getUnregisteredPatientsId(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1) FROM patients WHERE userID = :patientID AND userPass = :patientPass")
    suspend fun hasUserWithPass(patientID: Int, patientPass: String) : Boolean

    @Query("SELECT EXISTS(SELECT 1) FROM patients WHERE userID = :patientID AND phoneNo = :patientPhoneNum")
    suspend fun hasUserWithPhoneNum(patientID: Int, patientPhoneNum: String) : Boolean

    @Query("UPDATE patients SET userPass = :newPassword, userName = :newUsername WHERE userID = :userId")
    suspend fun updatePasswordAndUsername(userId: Int, newPassword: String, newUsername: String)

    @Query("UPDATE patients SET userPass = :newPassword WHERE userID = :userId")
    suspend fun updatePassword(userId: Int, newPassword: String)

    @Query("UPDATE patients SET userName = :newUsername WHERE userID = :userId")
    suspend fun updateUsername(userId: Int, newUsername: String)

    @Query("SELECT userSex, AVG(hEIFAtotalscore) AS averageHEIFA FROM patients GROUP BY userSex")
    fun getAverageHEIFAScore() : Flow<List<AverageHEIFA>>
}