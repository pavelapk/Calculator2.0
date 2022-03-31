package ru.pavelapk.calculator2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.gandiva.neumorphic.LightSource
import com.gandiva.neumorphic.neu
import com.gandiva.neumorphic.shape.Flat
import com.gandiva.neumorphic.shape.Pressed
import com.gandiva.neumorphic.shape.RoundedCorner
import ru.pavelapk.calculator2.ui.theme.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Calculator20Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Calculator()
                }
            }
        }
    }
}


@Composable
fun Calculator() {
    Column(Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.app_name),
            fontSize = 28.sp,
            fontFamily = appNameFontFamily,
            fontWeight = FontWeight.Medium,
            color = primaryTextColor,
            modifier = Modifier.padding(8.dp)
        )
        Box {
            Image(
                painterResource(R.drawable.img_screen),
                null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 16.dp)
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
                    "8888888888",
                    fontSize = 60.sp,
                    color = screenBackgroundTextColor,
                    fontFamily = screenFontFamily,
                )
                Text(
                    "1234",
                    fontSize = 60.sp, color = primaryTextColor,
                    fontFamily = screenFontFamily,
                    modifier = Modifier.align(Alignment.CenterEnd)
                )
            }
        }
        ButtonPad()
    }
}

@Composable
fun ButtonPad() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(bottom = 60.dp),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row {
            CalcBtn("AС", Modifier.weight(1f))
            CalcBtn("+/-", Modifier.weight(1f))
            CalcBtn("%", Modifier.weight(1f))
            CalcBtn("÷", Modifier.weight(1f), isBlueButton = true)
        }
        Row {
            CalcBtn("7", Modifier.weight(1f))
            CalcBtn("8", Modifier.weight(1f))
            CalcBtn("9", Modifier.weight(1f))
            CalcBtn("X", Modifier.weight(1f), isBlueButton = true)
        }
        Row {
            CalcBtn("4", Modifier.weight(1f))
            CalcBtn("5", Modifier.weight(1f))
            CalcBtn("6", Modifier.weight(1f))
            CalcBtn("-", Modifier.weight(1f), isBlueButton = true)
        }
        Row {
            CalcBtn("1", Modifier.weight(1f))
            CalcBtn("2", Modifier.weight(1f))
            CalcBtn("3", Modifier.weight(1f))
            CalcBtn("+", Modifier.weight(1f), isBlueButton = true)
        }
        Row {
            CalcBtn("0", Modifier.weight(2f), aspectRatio = 2f)
            CalcBtn(",", Modifier.weight(1f))
            CalcBtn("=", Modifier.weight(1f), isBlueButton = true)
        }
    }
}

@Composable
fun CalcBtn(
    text: String,
    modifier: Modifier,
    aspectRatio: Float = 1f,
    isBlueButton: Boolean = false
) {
    val context = LocalContext.current
    Button(
        onClick = { Toast.makeText(context, text, Toast.LENGTH_SHORT).show() },
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


@Preview(name = "Light Mode", showBackground = true)
@Composable
fun DefaultPreview() {
    Calculator()
}