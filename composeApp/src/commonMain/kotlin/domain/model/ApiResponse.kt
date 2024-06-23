package domain.model

import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse(
    val items: List<Sentence>,
    val page: Int,
    val perPage: Int,
    val totalItems: Int,
    val totalPages: Int
)
