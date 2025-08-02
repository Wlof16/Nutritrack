package com.fit2081.XuanRen_33523436.nutritrack

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.fit2081.XuanRen_33523436.nutritrack.BuildConfig

class GenAIService {
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    suspend fun sendPrompt(prompt: String): Result<String> {
        return try {
            val response = generativeModel.generateContent(
                content { text(prompt) }
            )
            val output = response.text ?: ""
            Result.success(output)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}