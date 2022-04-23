package ru.pavelapk.calculator2.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner
import ru.pavelapk.calculator2.R
import ru.pavelapk.calculator2.ui.theme.primaryTextColor
import ru.pavelapk.calculator2.ui.theme.screenBackgroundTextColor
import ru.pavelapk.calculator2.ui.theme.screenFontFamily
import ru.pavelapk.calculator2.ui.theme.screenInnerShadowColor

@Composable
fun Screen(text: String, modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        Image(
            painterResource(R.drawable.img_screen),
            null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
                .neu(
                    lightShadowColor = screenInnerShadowColor,
                    darkShadowColor = screenInnerShadowColor,
                    shadowElevation = 10.dp,
                    lightSource = LightSource.LEFT_TOP,
                    shape = Pressed(RoundedCorner(10.dp)),
                )
        )
        Box(modifier = Modifier.align(Alignment.Center)) {
            Text(
                stringResource(R.string.screen_placeholder),
                fontSize = 60.sp,
                color = screenBackgroundTextColor,
                fontFamily = screenFontFamily,
            )
            Text(
                text,
                fontSize = 60.sp, color = primaryTextColor,
                fontFamily = screenFontFamily,
                maxLines = 1,
                softWrap = false,
                modifier = Modifier.align(Alignment.CenterEnd)
            )
        }
    }
}