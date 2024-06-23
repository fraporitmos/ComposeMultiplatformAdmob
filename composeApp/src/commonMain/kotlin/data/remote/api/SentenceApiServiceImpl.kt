package data.remote.api

import domain.QuestionApiService
import domain.model.ApiResponse
import domain.model.RequestState
import domain.model.Sentence
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class SentenceApiServiceImpl : QuestionApiService {
    companion object {
        const val BASE_URL = "https://englishtutor.fraporitmos.com/api/collections/question/records"
        const val API_KEY = "f8c6417c-muce-2395-ympa-05f1bcb17db1"
    }

    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
        install(DefaultRequest) {
            headers {
                append("x-api-key", API_KEY)
            }
        }
    }


    override suspend fun getSentences(levelTense: Int, levelQuestion: Int): RequestState<List<Sentence>>{
        val url = "$BASE_URL?filter=(tense=$levelTense%26%26level=$levelQuestion)"
        return try {
            val response = httpClient.get(url)
            if (response.status.value == 200) {
                val apiResponse = Json.decodeFromString<ApiResponse>(response.body())
                RequestState.Success(data = apiResponse.items)
            } else {
                RequestState.Error(message = "HTTP Error Code: ${response.status}")
            }
        } catch (e: Exception) {
            RequestState.Error(message = e.message.toString())
        }
    }
}
