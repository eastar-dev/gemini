package dev.eastar.help

import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.BlockThreshold
import com.google.ai.client.generativeai.type.HarmCategory
import com.google.ai.client.generativeai.type.SafetySetting
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig

suspend fun prompt(content: String, mbti: String): String {
    val model = GenerativeModel(
        "gemini-1.5-flash",
        BuildConfig.apiKey,
        generationConfig = generationConfig {
            temperature = 1f
            topK = 64
            topP = 0.95f
            maxOutputTokens = 8192
            //responseMimeType = "text/plain"
        },
        safetySettings = listOf(
            SafetySetting(HarmCategory.HARASSMENT, BlockThreshold.LOW_AND_ABOVE),
            SafetySetting(HarmCategory.HATE_SPEECH, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.SEXUALLY_EXPLICIT, BlockThreshold.MEDIUM_AND_ABOVE),
            SafetySetting(HarmCategory.DANGEROUS_CONTENT, BlockThreshold.MEDIUM_AND_ABOVE),
        ),
    )


    val chatHistory = listOf(
        content("user") {
            text(content)
        },
    )

    val chat = model.startChat(chatHistory)

    return try {
        val response = chat.sendMessage("주어진 글에 대한 답변을 MBTI 성격분석중 $mbti 에 성격으로 답을 해줘")
        response.text?.let { outputContent ->
            outputContent
        } ?: "Empty response"
    } catch (e: Exception) {
        e.printStackTrace()
        e.localizedMessage ?: ""
    }
}