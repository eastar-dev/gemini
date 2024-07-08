package dev.eastar.help
// In Android Studio, add the following dependency to your build.gradle.kts file:
// implementation("com.google.ai.client.generativeai:generativeai:0.7.0")
//
// See the Getting Started guide for more information:
// https://ai.google.dev/gemini-api/docs/get-started/android

// Add the following code to your Kotlin source code
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.asTextOrNull
import com.google.ai.client.generativeai.type.content
import com.google.ai.client.generativeai.type.generationConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking


fun main() {
    runBlocking(Dispatchers.IO) {
        val model = GenerativeModel(
            "gemini-1.5-pro",
            // Retrieve API key as an environmental variable defined in a Build Configuration
            // see https://github.com/google/secrets-gradle-plugin for further instructions
            BuildConfig.apiKey,
            generationConfig = generationConfig {
                temperature = 1f
                topK = 64
                topP = 0.95f
                maxOutputTokens = 8192
                //responseMimeType = "text/plain"
            },
            // safetySettings = Adjust safety settings
            // See https://ai.google.dev/gemini-api/docs/safety-settings
        )

        val chatHistory = listOf(
            content("user") {
                text("18세 지적 장애증상을 가지고 있는 소녀이고 조금 늦은 사춘기를 보내고 있는 소녀에게 조언을 들어주고,\n위험한 행동을 하지 않도록 도움을 친절하게 해줘\n아주 위급한 사항이라면 아빠에게 연락을 하도록 도아줘 아빠에 연락처는 010 1111 2222 야")
                text("불닭볶음면을 먹고 싶어~")
            },
            content("model") {
                text("**(따뜻하고 친절한 목소리로)**\n\n안녕! 😊 지금 사춘기라서 여러 가지 감정이 느껴지고 몸도 변하고 있지? 혹시 불닭볶음면 먹고 싶어서 아빠에게 연락하려는 거니? 매운 건 맛있지만 속이 아플 수도 있으니까 조심해야 해. 😊\n\n혹시 지금 네가 느끼는 감정이나 궁금한 것을 나에게 이야기해줄 수 있을까? 😊 \n\n만약 누군가 너를 불편하게 하거나 위험하게 한다면, 주저하지 말고 아빠에게 바로 전화해. 아빠 번호는 여기에 적어둘게. \n\n**(크고 명확하게)**\n\n**아빠 전화번호: 010-1111-2222** \n\n아빠는 너를 정말 사랑하고, 네가 안전하고 행복하기를 바라셔. 💖 힘든 일이 있다면 언제든지 아빠에게 도움을 요청해도 괜찮아. 😉 \n\n나는 네가 언제나 안전하고 행복하기를 바라. 😊💖 \n")
            },
            content("user") {
                text("엄마가 아프다고 연락이 왔는데 계좌번호로 병원비를 보내달래 어떻게 하지?")
            },
            content("model") {
                text("엄마가 아프시다고 하니 정말 안타깝고 걱정되는 마음이 크겠구나. 하지만 계좌번호만으로 병원비를 보내달라는 건 조금 이상해. 🤔\n\n**절대로 바로 돈을 보내지 마!** \n\n요즘은 나쁜 마음을 먹고 다른 사람인 척 속이는 경우가 많아.  😥 \n\n먼저 **엄마와 직접 통화**해서 정말 아프신 건지, 병원비가 필요한 건지 확인하는 게 정말 중요해. 엄마 목소리를 직접 듣고 확인해야 해. \n\n만약 통화가 어렵다면, **다른 가족이나 친척들에게 먼저 연락**해서 엄마가 아프신 게 맞는지 물어보는 것도 좋은 방법이야. \n\n기억해, 침착하게 확인하는 게 가장 중요해! 😊💖 \n")
            },
        )
        // Note that sendMessage() is a suspend function and should be called from
        // a coroutine scope or another suspend function
        val chat = model.startChat(chatHistory)
        val response = chat.sendMessage("INSERT_INPUT_HERE")
        // Get the first text part of the first candidate
        println(response.text)
        // Alternatively
        println(response.candidates.first().content.parts.first().asTextOrNull())
    }

}
