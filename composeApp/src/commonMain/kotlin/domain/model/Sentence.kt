package domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Sentence(
    val alternatives: List<String>,
    val collectionId: String,
    val collectionName: String,
    val created: String,
    val feedback: String,
    val image: String,
    val id: String,
    val level: Int,
    val answer: String,
    val tense: Int,
    val question: String,
    val updated: String
)