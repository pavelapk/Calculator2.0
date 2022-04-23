package ru.pavelapk.calculator2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import ru.pavelapk.calculator2.calculator.CalcOperation
import ru.pavelapk.calculator2.calculator.CalcOperation.*
import ru.pavelapk.calculator2.calculator.CalculatorEngine
import ru.pavelapk.calculator2.calculator.CalculatorState
import ru.pavelapk.calculator2.calculator.MathOperation
import ru.pavelapk.calculator2.calculator.MathOperation.*

data class CalculatorUiState(
    val screenNumber: String,
    val operation: String?,
    val state: CalculatorState
)

class CalculatorViewModel : ViewModel() {
    private val calculator = CalculatorEngine()

    val uiState = combine(
        calculator.currentNumber,
        calculator.operation,
        calculator.state
    ) { num, operation, state ->
        CalculatorUiState(num, operation?.let { mathToScreenStr(it) }, state)
    }.stateIn(
        viewModelScope, SharingStarted.WhileSubscribed(),
        CalculatorUiState(
            "",
            null,
            CalculatorState.STATE_FIRST
        )
    )

    fun onDigitClick(digit: String) {
        calculator.onDigitClick(digit)
    }

    fun onOperationClick(operation: String) {
        btnStrToMath[operation]?.let {
            calculator.doMathOperation(it)
        }
            ?: strToCalc[operation]?.let {
                calculator.doCalcOperation(it)
            }
    }

    companion object {

        fun mathToScreenStr(operation: MathOperation) = when (operation) {
            PLUS -> "+"
            MINUS -> "-"
            MULT -> "*"
            DIV -> "/"
        }

        fun mathToStr(operation: MathOperation) = when (operation) {
            PLUS -> "+"
            MINUS -> "-"
            MULT -> "X"
            DIV -> "÷"
        }

        val btnStrToMath = MathOperation.values().associateBy { v -> mathToStr(v) } // reverse

        fun calcToStr(operation: CalcOperation) = when (operation) {
            EQUALLY -> "="
            PLUS_MINUS -> "+/-"
            PERCENT -> "%"
            ALL_CLEAR -> "AC"
        }

        val strToCalc = CalcOperation.values().associateBy { v -> calcToStr(v) } // reverse
    }

}