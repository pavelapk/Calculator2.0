package ru.pavelapk.calculator2.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import ru.pavelapk.calculator2.calculator.CalcOperation.*
import ru.pavelapk.calculator2.calculator.CalculatorEngine.Companion.COMMA
import ru.pavelapk.calculator2.CalculatorViewModel.Companion.calcToStr
import ru.pavelapk.calculator2.CalculatorViewModel.Companion.mathToStr
import ru.pavelapk.calculator2.calculator.MathOperation.*
import ru.pavelapk.calculator2.ui.ButtonPad.EIGHT
import ru.pavelapk.calculator2.ui.ButtonPad.FIVE
import ru.pavelapk.calculator2.ui.ButtonPad.FOUR
import ru.pavelapk.calculator2.ui.ButtonPad.NINE
import ru.pavelapk.calculator2.ui.ButtonPad.ONE
import ru.pavelapk.calculator2.ui.ButtonPad.SEVEN
import ru.pavelapk.calculator2.ui.ButtonPad.SIX
import ru.pavelapk.calculator2.ui.ButtonPad.THREE
import ru.pavelapk.calculator2.ui.ButtonPad.TWO
import ru.pavelapk.calculator2.ui.ButtonPad.ZERO

object ButtonPad {
    const val ONE = "1"
    const val TWO = "2"
    const val THREE = "3"
    const val FOUR = "4"
    const val FIVE = "5"
    const val SIX = "6"
    const val SEVEN = "7"
    const val EIGHT = "8"
    const val NINE = "9"
    const val ZERO = "0"
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
            CalcBtnInRow(calcToStr(ALL_CLEAR), onOperationClick)
            CalcBtnInRow(calcToStr(PLUS_MINUS), onOperationClick)
            CalcBtnInRow(calcToStr(PERCENT), onOperationClick)
            CalcBtnInRow(mathToStr(DIV), onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow(SEVEN, onDigitClick)
            CalcBtnInRow(EIGHT, onDigitClick)
            CalcBtnInRow(NINE, onDigitClick)
            CalcBtnInRow(mathToStr(MULT), onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow(FOUR, onDigitClick)
            CalcBtnInRow(FIVE, onDigitClick)
            CalcBtnInRow(SIX, onDigitClick)
            CalcBtnInRow(mathToStr(MINUS), onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow(ONE, onDigitClick)
            CalcBtnInRow(TWO, onDigitClick)
            CalcBtnInRow(THREE, onDigitClick)
            CalcBtnInRow(mathToStr(PLUS), onOperationClick, isBlueButton = true)
        }
        Row {
            CalcBtnInRow(ZERO, onDigitClick, weight = 2f, aspectRatio = 2f)
            CalcBtnInRow(COMMA, onDigitClick)
            CalcBtnInRow(calcToStr(EQUALLY), onOperationClick, isBlueButton = true)
        }
    }
}