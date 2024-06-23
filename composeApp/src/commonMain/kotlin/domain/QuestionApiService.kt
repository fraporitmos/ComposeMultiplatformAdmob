package domain

import domain.model.RequestState
import domain.model.Sentence

interface QuestionApiService {
    suspend fun getSentences(levelTense: Int, levelQuestion: Int): RequestState<List<Sentence>>
}