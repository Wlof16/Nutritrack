package com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.Patient

@Entity(
    tableName = "food_intakes",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userID"],
        childColumns = ["patientId"],
        onDelete = CASCADE
    )]
)
data class FoodIntake (
    @PrimaryKey(autoGenerate = false)
    val patientId: Int,

    val checked1: Boolean, //fruit

    val checked2: Boolean, //Vegetables

    val checked3: Boolean, //Grains

    val checked4: Boolean, //Red Meat

    val checked5: Boolean, //Seafood

    val checked6: Boolean, //Poultry

    val checked7: Boolean, //Fish

    val checked8: Boolean, //Eggs

    val checked9: Boolean, //Nuts/Seeds

    val selectedOptionText: String, //Persona

    val mTime1: String, //Time eat biggest meal

    val mTime2: String, //Time Sleep

    val mTime3: String //Time Wake Up
)