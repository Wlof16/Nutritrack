package com.fit2081.XuanRen_33523436.nutritrack.data.fruit

class FruityRepository {
    private val apiService = FruityViceAPI.create()

    suspend fun getFruit(fruit: String) : FruitResponseModel? {
        val response = apiService.getFruit(fruit)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}