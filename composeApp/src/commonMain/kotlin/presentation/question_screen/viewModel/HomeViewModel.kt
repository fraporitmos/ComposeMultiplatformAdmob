package presentation.question_screen.viewModel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import cafe.adriel.voyager.core.model.ScreenModel
import domain.QuestionApiService
import domain.model.Sentence
import androidx.compose.runtime.State
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    private val api: QuestionApiService
) : ScreenModel {

    private var _sentencesList: MutableState<List<Sentence>> = mutableStateOf(emptyList())
    val sentencesList: State<List<Sentence>> get() = _sentencesList

    private var _loading: MutableState<Boolean> = mutableStateOf(false)
    val loading: State<Boolean> get() = _loading

    private var _error: MutableState<String?> = mutableStateOf(null)
    val error: State<String?> get() = _error


     fun fetchSentences(
        levelTense: Int, levelQuestion: Int
    ) {
        _loading.value = true
        _error.value = null

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val sentences = withContext(Dispatchers.IO) { api.getSentences(levelTense,levelQuestion )}
                _sentencesList.value = sentences.getSuccessData()
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _loading.value = false
            }
        }
    }
}
