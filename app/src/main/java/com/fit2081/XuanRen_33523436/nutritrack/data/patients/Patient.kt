package com.fit2081.XuanRen_33523436.nutritrack.data.patients

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient (
//    Basic Patient Info
    @PrimaryKey(autoGenerate = false)
    val userID: Int,

    val phoneNo: String,

    val userName: String?,

    val userSex: String,

    val userPass: String?,

//    Patient Nutrition Score Info
    val vegetablesHEIFAscore: Double,
    val fruitHEIFAscore: Double,
    val grainsandcerealsHEIFAscore: Double,
    val wholegrainsHEIFAscore: Double,
    val meatandalternativesHEIFAscore: Double,
    val dairyandalternativesHEIFAscore: Double,
    val waterHEIFAscore: Double,
    val unsaturatedFatHEIFAscore: Double,
    val saturatedFatHEIFAscore: Double,
    val sodiumHEIFAscore: Double,
    val sugarHEIFAscore: Double,
    val alcoholHEIFAscore: Double,
    val discretionaryHEIFAscore: Double,
    val hEIFAtotalscore: Double,
    val fruitservesize: Double,
    val fruitvariationsscore: Double,

















    )