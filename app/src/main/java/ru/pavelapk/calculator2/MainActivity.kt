package ru.pavelapk.calculator2

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
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
import ru.pavelapk.calculator2.CalcOperation.*
import ru.pavelapk.calculator2.CalculatorEngine.Companion.COMMA
import ru.pavelapk.calculator2.MathOperation.*
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

        val calculator = remember { CalculatorEngine() }
        var screenText by remember { mutableStateOf("") }
        val context = LocalContext.current

        Screen(text = screenText, modifier = Modifier.padding(8.dp, 16.dp))

        ButtonPad(
            onDigitClick = {
                screenText = stringForDisplay(context, calculator.onDigitClick(it))
            },
            onOperationClick = {
                screenText = stringForDisplay(
                    context,
                    calculator.onOperationClick(it),
                    if (calculator.getState() == State.STATE_OPERATION)
                        calculator.getOperationStr()
                    else null
                )
            },
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 60.dp)
        )
    }
}

fun stringForDisplay(context: Context, num: String?, operation: String? = null): String {
    if (num == null) return context.getString(R.string.error)

    var result = if (num.length > CalculatorEngine.MAX_LENGTH) {
        context.getString(R.string.trunc_num, num.take(CalculatorEngine.MAX_LENGTH - 2))
    } else {
        num
    }
    if (operation != null) {
        result += operation
    }
    return result
}

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

@Composable
fun ButtonPad(
    onDigitClick: (String) -> Unit,
    onOperationClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Row {
            CalcBtnInRow(ALL_CLEAR.btnStr, onOperationClick)
            CalcBtnInRow(PLUS_MINUS.btnStr, onOperationClick)
            CalcBtnInRow(PERCENT.btnStr, onOperationClick)
            CalcBtnInRow(DIV.btnStr, onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow("7", onDigitClick)
            CalcBtnInRow("8", onDigitClick)
            CalcBtnInRow("9", onDigitClick)
            CalcBtnInRow(MULT.btnStr, onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow("4", onDigitClick)
            CalcBtnInRow("5", onDigitClick)
            CalcBtnInRow("6", onDigitClick)
            CalcBtnInRow(MINUS.btnStr, onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow("1", onDigitClick)
            CalcBtnInRow("2", onDigitClick)
            CalcBtnInRow("3", onDigitClick)
            CalcBtnInRow(PLUS.btnStr, onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow("0", onDigitClick, weight = 2f, aspectRatio = 2f)
            CalcBtnInRow(COMMA, onDigitClick)
            CalcBtnInRow(EQUALLY.btnStr, onOperationClick, isBlueButton = true)
        }
    }
}

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


@Preview(name = "Light Mode", showBackground = true)
@Composable
fun DefaultPreview() {
    Calculator()
}