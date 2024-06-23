package presentation.level_screen

import Platform
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import englishtense.composeapp.generated.resources.Res
import englishtense.composeapp.generated.resources.bubble
import englishtense.composeapp.generated.resources.capybara_reading
import getPlatform
import org.jetbrains.compose.resources.painterResource
import presentation.question_screen.QuestionScreen
import showInterstitialAd


class LevelScreen(private val levelTense: Int) : Screen {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Scaffold (
            topBar = {
                TopAppBar(
                    title = { Text(text = "Present Simple") },
                    navigationIcon =
                        {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = "Back"
                                )
                            }
                        }
                )
            },
        ){ padding ->

            Column {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth().padding(top = 64.dp)
                ) {
                    Image(
                        painter = painterResource(Res.drawable.capybara_reading),
                        contentDescription = "Capybara default",
                        modifier = Modifier
                            .weight(1.5f)
                            .size(120.dp),
                    )
                    Box(
                        modifier = Modifier
                            .weight(3f)
                            .height(IntrinsicSize.Min)
                            .align(Alignment.CenterVertically)
                            .padding(vertical = 16.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.CenterEnd)
                        ) {
                            Image(
                                painter = painterResource(Res.drawable.bubble),
                                contentDescription = "Capybara default",
                                modifier = Modifier
                                    .size(250.dp)
                                    .align(Alignment.CenterEnd),
                                contentScale = ContentScale.Fit
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
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 24.dp),
                ) {
                    val levels = (1..24).toList()
                    items(levels) { levelQuestion ->
                        FloatingActionButton(
                            shape = CircleShape,
                            containerColor = if(levelQuestion == 1) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.surfaceContainerLow,
                            onClick = {
                                 navigator.push(QuestionScreen(levelTense,levelQuestion))
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .border(
                                    BorderStroke(1.dp, MaterialTheme.colorScheme.primary), // Aquí defines el ancho y color del borde
                                    CircleShape
                                ),
                            contentColor = MaterialTheme.colorScheme.tertiary

                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if(levelQuestion > 1){
                                Icon(
                                    Icons.Default.Lock,
                                    contentDescription = "Arrow Icon",
                                    modifier = Modifier.size(24.dp).padding(end = 4.dp),
                                )
                                }
                                Text(
                                    text = levelQuestion.toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if(levelQuestion == 1) Color.White
                                    else  MaterialTheme.colorScheme.tertiary
                                    )
                            }
                        }
                    }
                }
            }
        }

    }
}
