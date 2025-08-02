package com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.Patient

@Entity(tableName = "nutricoach_tips",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userID"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.CASCADE
    )])
data class NutriCoachTip (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val patientId: Int,

    val tipMessage: String
)