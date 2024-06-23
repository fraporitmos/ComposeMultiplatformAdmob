package presentation.question_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LinearProgressBar(
    modifier: Modifier = Modifier,
    options: LinearProgressBarOptions = LinearProgressBarDefaults.linearProgressBarColorOptions,
    progress : Float
) {
    val animatedProgress by animateFloatAsState(targetValue = progress)
    val density = LocalDensity.current
    Canvas(modifier = modifier
        .clip(options.cornerShape)
        .background(options.colors.backgroundColor)) {
        drawRoundRect(
            color = options.colors.primaryColor,
            size = Size(height = size.height, width = size.width * animatedProgress),
            cornerRadius = options.getCornerRadius(size, density)
        )

        if (progress != 0f)
            drawRoundRect(
                color = options.colors.secondaryColor,
                topLeft = Offset(options.progressStartPadding.toPx(), options.progressTopPadding.toPx()),
                size = Size(height = size.height * options.progressHeightScale, width = size.width * animatedProgress - options.progressStartPadding.toPx() * 2),
                cornerRadius = options.getCornerRadius(size, density)
            )
    }

}




@Stable
class LinearProgressBarOptions(
    val cornerShape : RoundedCornerShape = CircleShape,
    val colors : ColorOptions = ColorOptions(),
    val progressHeightScale : Float = 0.25f,
    val progressStartPadding : Dp = 24.dp,
    val progressTopPadding : Dp = 6.dp
) {
    fun getCornerRadius(size: Size, density: Density) : CornerRadius {
        return CornerRadius(cornerShape.topStart.toPx(size, density), cornerShape.topStart.toPx(size, density))
    }
}

object LinearProgressBarDefaults {
    val linearProgressBarColorOptions = LinearProgressBarOptions(colors = ColorOptions(
        backgroundColor = Color( 0xFFF4DED5) , primaryColor =  Color(0xFFFF8B26), secondaryColor =  Color(0xFFFF8B26)
    ))
}


@Immutable
class ColorOptions (
    val backgroundColor : Color = Color.Unspecified,
    val primaryColor: Color = Color.Unspecified,
    val secondaryColor: Color = Color.Unspecified
)