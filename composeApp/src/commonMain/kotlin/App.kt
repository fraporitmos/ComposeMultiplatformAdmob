
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import data.di.initializeKoin
import org.jetbrains.compose.ui.tooling.preview.Preview
import presentation.home_screen.HomeScreen

@Composable
@Preview
fun App() {

    val colors = if(!isSystemInDarkTheme()){
        lightScheme
    }else{
        darkScheme
    }
    initializeKoin()

    MaterialTheme (
        colorScheme = colors,

    ){
       Navigator(
           screen = HomeScreen()
       ){
           FadeTransition(it)
       }
    }
}

