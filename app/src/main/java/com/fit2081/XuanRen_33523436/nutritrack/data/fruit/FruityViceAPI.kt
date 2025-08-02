package com.fit2081.XuanRen_33523436.nutritrack.data.fruit

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface FruityViceAPI {

    @GET("{fruit}")
    suspend fun getFruit(@Path("fruit") fruit: String) : Response<FruitResponseModel>

    companion object {
        var BASE_URL = "https://www.fruityvice.com/api/fruit/"

        fun create(): FruityViceAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(FruityViceAPI::class.java)
        }
    }
}