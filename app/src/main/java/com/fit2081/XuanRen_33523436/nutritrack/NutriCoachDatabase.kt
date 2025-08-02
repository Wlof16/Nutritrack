package com.fit2081.XuanRen_33523436.nutritrack

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes.FoodIntake
import com.fit2081.XuanRen_33523436.nutritrack.data.foodIntakes.FoodIntakeDao
import com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips.NutriCoachTip
import com.fit2081.XuanRen_33523436.nutritrack.data.nutricoachtips.NutriCoachTipDao
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.Patient
import com.fit2081.XuanRen_33523436.nutritrack.data.patients.PatientDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@Database(entities = [Patient::class, FoodIntake::class, NutriCoachTip::class], version = 1, exportSchema = false)
abstract class NutriCoachDatabase: RoomDatabase() {

    abstract fun patientDao(): PatientDao
    abstract fun foodIntakeDao(): FoodIntakeDao
    abstract fun nutriCoachTipDao(): NutriCoachTipDao

    companion object {
        @Volatile
        private var Instance: NutriCoachDatabase? = null

        fun getDatabase(context: Context): NutriCoachDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, NutriCoachDatabase::class.java, "item_database")
                    .addCallback(object : Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)

                            CoroutineScope(Dispatchers.IO).launch {
                                val patientDao = getDatabase(context).patientDao()
                                val csvRowData = getAllRowsInCSV(context, "data.csv")
                                val headerToFind = listOf<String>("VegetablesHEIFAscoreMale","FruitHEIFAscoreMale","GrainsandcerealsHEIFAscoreMale","WholegrainsHEIFAscoreMale", "MeatandalternativesHEIFAscoreMale", "DairyandalternativesHEIFAscoreMale", "WaterHEIFAscoreMale", "UnsaturatedFatHEIFAscoreMale", "SaturatedFatHEIFAscoreMale", "SodiumHEIFAscoreMale", "SugarHEIFAscoreMale", "AlcoholHEIFAscoreMale", "DiscretionaryHEIFAscoreMale", "HEIFAtotalscoreMale", "Fruitservesize", "Fruitvariationsscore")
                                val indexMale = getIndexBasedOnHeaderCSV(context, "data.csv", headerToFind)
                                //This is since the last two items are not gender based so no increment
                                val indexFemale = indexMale.mapIndexed { idx, value ->
                                    if (idx < indexMale.size - 2) value + 1 else value }

                                csvRowData.forEach { row ->
                                    var index: List<Int> = indexMale
                                    if (row[2] == "Female") {
                                        index = indexFemale
                                    }
//                                    Log.d("userID", row[1])
//                                    Log.d("index", index.toString())
                                    val newPatient = Patient(
                                        phoneNo = row[0],
                                        userID = row[1].toInt(),
                                        userName = null,
                                        userSex = row[2],
                                        userPass = null,

                                        vegetablesHEIFAscore = row[index[0]].toDouble(),
                                        fruitHEIFAscore = row[index[1]].toDouble(),
                                        grainsandcerealsHEIFAscore = row[index[2]].toDouble(),
                                        wholegrainsHEIFAscore = row[index[3]].toDouble(),
                                        meatandalternativesHEIFAscore = row[index[4]].toDouble(),
                                        dairyandalternativesHEIFAscore = row[index[5]].toDouble(),
                                        waterHEIFAscore = row[index[6]].toDouble(),
                                        unsaturatedFatHEIFAscore = row[index[7]].toDouble(),
                                        saturatedFatHEIFAscore = row[index[8]].toDouble(),
                                        sodiumHEIFAscore = row[index[9]].toDouble(),
                                        sugarHEIFAscore = row[index[10]].toDouble(),
                                        alcoholHEIFAscore = row[index[11]].toDouble(),
                                        discretionaryHEIFAscore = row[index[12]].toDouble(),
                                        hEIFAtotalscore = row[index[13]].toDouble(),
                                        fruitservesize = row[index[14]].toDouble(),
                                        fruitvariationsscore = row[index[15]].toDouble(),
                                    )
                                    patientDao.insert(newPatient)
                                }
                            }
                        }
                    })
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

fun getAllRowsInCSV(context: Context, fileName: String): List<List<String>> {
    val assets = context.assets
    val result = mutableListOf<List<String>>()

    try {
        val inputStream = assets.open(fileName)
        val reader = BufferedReader(InputStreamReader(inputStream))
        reader.useLines { lines ->
            lines.drop(1).forEach { line ->  // Skip header
                val values = line.split(",")
                result.add(values)
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("getUserRowCSV", "Error")
    }

    return result
}

fun getIndexBasedOnHeaderCSV(context: Context, fileName: String, headerToFind: List<String>): List<Int> {
    val assets = context.assets
    var header: List<String>

    try {
        val inputStream = assets.open(fileName)
        header = BufferedReader(InputStreamReader(inputStream)).useLines { lines ->
            lines.first().split(",")
        }
    } catch (e: Exception) {
        e.printStackTrace()
        Log.d("Error", "Failed to read header")
        header = emptyList()
    }

    return headerToFind.map { header.indexOf(it) }
}



