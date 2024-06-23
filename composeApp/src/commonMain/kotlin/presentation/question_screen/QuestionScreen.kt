package presentation.question_screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import domain.model.PlatformType
import getPlatform
import io.kamel.core.Resource
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import presentation.finish_screen.FinishScreen
import presentation.question_screen.viewModel.HomeViewModel
import showInterstitialAd

class QuestionScreen(
    private val levelTense: Int,
    private val levelQuestion: Int
) : Screen {
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.currentOrThrow
        val viewModel: HomeViewModel = getScreenModel()
        val sentencesList by viewModel.sentencesList
        val isLoading by viewModel.loading
        val errorMessage by viewModel.error
        var isCheckQuestion by remember { mutableStateOf(false) }
        var currentIndex by remember { mutableStateOf(0) }
        var points by remember { mutableStateOf(0) }
        var alternativeSelected by remember { mutableStateOf("") }
        var progress by remember { mutableStateOf(0.0f) }

        LaunchedEffect(Unit) {
            viewModel.fetchSentences(
                levelTense, levelQuestion
            )
        }
        Scaffold(
            topBar = {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 64.dp, start = 16.dp, end = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearProgressBar(
                        modifier = Modifier.weight(1f).height(24.dp),
                        progress = progress
                    )

                }
            },
        ) { padding ->
            if (sentencesList.isEmpty()) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxSize()

                ) {
                    Spacer(modifier = Modifier.weight(1f))

                    CircularProgressIndicator(
                        modifier = Modifier.size(34.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.primary,
                        trackColor = MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

            } else {
                val painterResource: Resource<Painter> =
                    asyncPainterResource(sentencesList[currentIndex].image)
                Box(
                    modifier = Modifier.fillMaxSize().padding(top = 64.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        val text = sentencesList[currentIndex].question
                        val parts = text.split("_")

                        Box(
                            modifier = Modifier
                                .padding(top = 46.dp, bottom = 16.dp)
                        ) {
                            KamelImage(
                                resource = painterResource,
                                contentScale = ContentScale.Crop,
                                contentDescription = "Profile",
                                modifier = Modifier.size(
                                    if (getPlatform().name == PlatformType.IOS) {
                                        200.dp
                                    } else {
                                        200.dp
                                    }
                                ).clip(RoundedCornerShape(36.dp)),

                                onLoading = { progress -> CircularProgressIndicator(progress) },
                            )
                        }


                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp)
                        ) {
                            Text(
                                text = parts[0],
                                fontSize = if (getPlatform().name == PlatformType.IOS) {
                                    16.sp
                                } else {
                                    20.sp
                                },
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = " ___ ",
                                fontSize = if (getPlatform().name == PlatformType.IOS) {
                                    16.sp
                                } else {
                                    20.sp
                                },
                                fontWeight = FontWeight.Medium,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                text = parts[1],
                                fontSize = if (getPlatform().name == PlatformType.IOS) {
                                    16.sp
                                } else {
                                    20.sp
                                },
                                fontWeight = FontWeight.Medium
                            )
                        }

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            items(sentencesList[currentIndex].alternatives) { alternative ->
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            isCheckQuestion = true
                                            alternativeSelected = alternative
                                            if(alternativeSelected == sentencesList[currentIndex].answer){
                                                points+=1
                                                println("pointssss ${points}")
                                            }
                                        }
                                        .fillMaxWidth()
                                        .padding(8.dp)
                                        .background(MaterialTheme.colorScheme.surfaceContainer)
                                        .border(
                                            BorderStroke(
                                                1.dp,
                                                if (!isCheckQuestion) {
                                                    Color(0XFF55433C)
                                                } else if (alternative == sentencesList[currentIndex].answer) {
                                                    MaterialTheme.colorScheme.primary
                                                } else if (alternative == alternativeSelected) {
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                } else {
                                                    MaterialTheme.colorScheme.surface

                                                }
                                            ),
                                            shape = RoundedCornerShape(8.dp),

                                            ),
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Text(
                                            text = alternative,
                                            fontSize = if (getPlatform().name == PlatformType.IOS) {
                                                14.sp
                                            } else {
                                                18.sp
                                            },
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(
                                                if (getPlatform().name == PlatformType.IOS) {
                                                    10.dp
                                                } else {
                                                    12.dp
                                                }
                                            )
                                        )

                                        Spacer(modifier = Modifier.weight(1f))

                                        if (isCheckQuestion) {

                                            if (alternative == sentencesList[currentIndex].answer) {
                                                Icon(
                                                    Icons.Default.Check,
                                                    contentDescription = "Arrow Icon",
                                                    modifier = Modifier.size(32.dp)
                                                        .padding(end = 8.dp),
                                                    tint = MaterialTheme.colorScheme.primary,
                                                )
                                            } else if (alternative == alternativeSelected) {
                                                Icon(
                                                    Icons.Default.Close,
                                                    contentDescription = "Arrow Icon",
                                                    modifier = Modifier.size(32.dp)
                                                        .padding(end = 8.dp),
                                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (isCheckQuestion &&
                            alternativeSelected != sentencesList[currentIndex].answer
                        ) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth().padding(top = 8.dp, start = 24.dp, end = 24.dp)
                                    .background(MaterialTheme.colorScheme.surfaceContainer)
                            ) {
                                Text(
                                    sentencesList[currentIndex].feedback,
                                    modifier = Modifier.fillMaxWidth(
                                        if (getPlatform().name == PlatformType.IOS) {
                                            0.85f
                                        } else {
                                            1f
                                        }
                                    ).padding(12.dp),
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = if (getPlatform().name == PlatformType.IOS) {
                                        11.sp
                                    } else {
                                        14.sp
                                    }
                                )
                            }
                        }
                    }

                    Box(
                        modifier = Modifier.fillMaxSize().zIndex(1f).padding(
                            end = 24.dp, bottom = if (getPlatform().name == PlatformType.Android) {
                                74.dp
                            } else {
                                36.dp
                            }
                        ),
                        contentAlignment = Alignment.BottomEnd,
                    ) {
                        if (isCheckQuestion) {

                            FloatingActionButton(
                                onClick = {
                                    if (currentIndex >= sentencesList.size - 1) {
                                        if (getPlatform().name == PlatformType.Android) {
                                            progress += 0.1f
                                            showInterstitialAd{
                                                navigator.push(FinishScreen(
                                                    points
                                                ))
                                            }
                                        }else{
                                            navigator.push(FinishScreen(
                                                points
                                            ))
                                        }
                                    } else {
                                        isCheckQuestion = false
                                        currentIndex += 1
                                        progress += 0.1f
                                    }
                                },
                            ) {
                                Icon(Icons.Filled.ArrowForward, "Floating action button.")
                            }
                        }
                    }
                }
            }
        }
    }
}