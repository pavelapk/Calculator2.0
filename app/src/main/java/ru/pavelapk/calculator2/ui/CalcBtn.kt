package ru.pavelapk.calculator2.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.RoundedCorner
import ru.pavelapk.calculator2.ui.theme.*

@Composable
fun RowScope.CalcBtnInRow(
    text: String,
    onClick: (String) -> Unit,
    weight: Float = 1f,
    aspectRatio: Float = 1f,
    isBlueButton: Boolean = false
) {
    CalcBtn(text, onClick, Modifier.weight(weight), aspectRatio, isBlueButton)
}

@Composable
fun CalcBtn(
    text: String,
    onClick: (String) -> Unit,
    modifier: Modifier,
    aspectRatio: Float = 1f,
    isBlueButton: Boolean = false
) {
    Button(
        onClick = { onClick(text) },
        contentPadding = PaddingValues(0.dp),
        modifier = modifier
            .aspectRatio(aspectRatio)
            .fillMaxSize()
            .padding(6.dp)
            .neu(
                lightShadowColor = lightShadowColor,
                darkShadowColor = darkShadowColor,
                shadowElevation = 6.dp,
                lightSource = LightSource.LEFT_TOP,
                shape = Flat(RoundedCorner(20.dp)),
            ),
        colors = if (isBlueButton)
            ButtonDefaults.buttonColors(
                backgroundColor = blueBtnColor,
                contentColor = whiteBtnColor
            ) else
            ButtonDefaults.buttonColors(
                backgroundColor = whiteBtnColor,
                contentColor = blueBtnColor
            ),
        shape = RoundedCornerShape(20.dp),
        elevation = null
    ) {
        Text(
            text,
            maxLines = 1,
            softWrap = false,
            fontSize = 24.sp,
            fontFamily = buttonFontFamily
        )
    }
}