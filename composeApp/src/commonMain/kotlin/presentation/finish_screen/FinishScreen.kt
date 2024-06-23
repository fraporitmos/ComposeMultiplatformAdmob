package presentation.finish_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import englishtense.composeapp.generated.resources.Res
import englishtense.composeapp.generated.resources.bubble
import englishtense.composeapp.generated.resources.capybara_money
import org.jetbrains.compose.resources.painterResource
import presentation.question_screen.LinearProgressBar

class FinishScreen(private val points: Int) : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth().padding(top = 48.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.capybara_money),
                    contentDescription = "Capybara default",
                    modifier = Modifier
                        .weight(1.5f)
                        .size(160.dp)
                )

                Text(
                    "¡Congratulations! Continue your streak and unlock more levels",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,
                )
                Box(
                    modifier = Modifier
                        .weight(3f)
                        .height(IntrinsicSize.Min)
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .align(Alignment.Center)
                    ) {
                        Image(
                            painter = painterResource(Res.drawable.bubble),
                            contentDescription = "Capybara default",
                            modifier = Modifier
                                .size(300.dp)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Fit
                        )
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            "+2 coins",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Green,

                            )
                        Text(
                            "${points}/10",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(vertical = 24.dp)
                        )
                        Button(
                            onClick = { navigator.popUntilRoot() }
                        ) {
                            Text("Continuar")
                        }
                    }
                }
            }
        }
    }
}