package data.di

import com.russhwolf.settings.Settings
import data.remote.api.SentenceApiServiceImpl
import domain.QuestionApiService
import org.koin.core.context.startKoin
import org.koin.dsl.module
import presentation.question_screen.viewModel.HomeViewModel

val appModule = module {
    single { Settings() }
    single<QuestionApiService> { SentenceApiServiceImpl() }
    factory {
        HomeViewModel(
            api = get()
        )
    }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}