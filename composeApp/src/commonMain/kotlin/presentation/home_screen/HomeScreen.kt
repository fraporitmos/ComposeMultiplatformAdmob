package presentation.home_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.Level
import englishtense.composeapp.generated.resources.Res
import englishtense.composeapp.generated.resources.bubble
import englishtense.composeapp.generated.resources.capybara_default
import org.jetbrains.compose.resources.painterResource
import presentation.level_screen.LevelScreen

class HomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold { padding ->

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 48.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.capybara_default),
                        contentDescription = "Capybara default",
                        modifier = Modifier
                            .weight(1.5f)
                            .size(120.dp)
                    )
                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .height(IntrinsicSize.Min)
                            .align(Alignment.CenterVertically)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.CenterEnd)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.bubble),
                                contentDescription = "Capybara default",
                                modifier = Modifier
                                    .size(200.dp)
                                    .align(Alignment.CenterEnd),
                                contentScale = ContentScale.FillHeight
                            )
                        }
                        Text(
                            "Welcome to back, Are you ready to improve your english skills? ",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(end = 12.dp)
                        )
                    }
                }
                LazyColumn(modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)) {
                    items(tenses) { sentence ->
                        LevelItem(
                            title = sentence.title,
                            description = sentence.record,
                            progress = sentence.progress,
                            onClick = {
                                navigator.push(LevelScreen(sentence.level))
                                println(sentence.title)
                            }
                        )
                    }
                }
            }
        }
    }
}


val tenses = listOf(
    Level(1, "Present Simple", "0.4",0.3f),
    Level(2, "Present Continuous","0/10",0.0f),
    Level(3, "Present Perfect","0/10",0.0f),
    Level(4, "Present Perfect Continuous","0/10",0.0f),
    Level(5, "Past Simple","0/10",0.0f),
    Level(6, "Past Continuous","0/10",0.0f),
    Level(7, "Past Perfect","0/10",0.0f),
    Level(8, "Past Perfect Continuous","0/10",0.0f),
    Level(9, "Future Simple","0/10",0.0f),
    Level(10, "Future Continuous","0/10",0.0f),
    Level(11, "Future Perfect","0/10",0.0f),
    Level(12, "Future Perfect Continuous","0/10",0.0f)
)

@Composable
fun LevelItem(
    title: String,
    description: String,
    progress: Float,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                progress = progress,
                modifier = Modifier.size(34.dp),
                strokeWidth = 2.dp,
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onPrimary,

                )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = description,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.tertiary,
                    fontWeight = FontWeight.Bold
                )
            }
            OutlinedButton(onClick = { onClick() },
                modifier= Modifier.size(50.dp),
                shape = CircleShape,
                border= BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                contentPadding = PaddingValues(0.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                            contentColor =  Color.White)
            ) {
                Icon(Icons.Default.ArrowForward, contentDescription = "Arrow Icon",modifier = Modifier.size(24.dp))
            }

        }
    }
}

