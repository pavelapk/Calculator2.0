package ru.pavelapk.calculator2.calculator

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.pavelapk.calculator2.calculator.CalcOperation.*
import ru.pavelapk.calculator2.calculator.CalculatorState.*
import ru.pavelapk.calculator2.calculator.MathOperation.*
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class CalculatorEngine {

    companion object {
        const val MAX_LENGTH = 9
        const val COMMA = ","
        const val DOT = "."
        const val ZERO = "0"

        private const val FORMAT_PATTERN = "0"
        private val df = DecimalFormat(
            FORMAT_PATTERN,
            DecimalFormatSymbols.getInstance(Locale.ENGLISH)
        ).apply { maximumFractionDigits = MAX_LENGTH }
    }

    private var firstNumber = 0.0
    private var secondNumber = 0.0
    private var isComma = false

    private val _operation = MutableStateFlow<MathOperation?>(null)
    val operation: StateFlow<MathOperation?> = _operation

    private val _currentNumber = MutableStateFlow("")
    val currentNumber: StateFlow<String> = _currentNumber

    private val _state = MutableStateFlow(STATE_FIRST)
    val state: StateFlow<CalculatorState> = _state

    fun onDigitClick(text: String) {
        if (_state.value == STATE_OPERATION) {
            _state.value = STATE_SECOND
            clear()
        }
        if (_currentNumber.value.length < MAX_LENGTH) {
            when (text) {
                COMMA -> {
                    if (!isComma) {
                        _currentNumber.value += DOT
                        isComma = true
                    }
                }
                else -> {
                    if (!(_currentNumber.value == ZERO && text == ZERO)) {
                        _currentNumber.value += text
                    }
                }
            }

        }
    }

    fun doCalcOperation(operation: CalcOperation) {
        when (operation) {
            ALL_CLEAR -> allClear()
            EQUALLY -> {
                if (_currentNumber.value.isNotEmpty() && _state.value == STATE_SECOND) {
                    secondNumber = _currentNumber.value.toDouble()
                    if (!calculate()) {
                        _currentNumber.value = ""
                        return
                    }
                    _state.value = STATE_FIRST
                }
            }
            PLUS_MINUS -> {
                if (_currentNumber.value.isNotEmpty()) {
                    _currentNumber.value = df.format(-_currentNumber.value.toDouble())
                }
            }
            PERCENT -> {
                if (_currentNumber.value.isNotEmpty() && _state.value == STATE_SECOND) {
                    secondNumber = _currentNumber.value.toDouble()
                    if (!calculateWithPercent()) {
                        _currentNumber.value = ""
                        return
                    }
                    _state.value = STATE_FIRST
                }
            }
        }
    }

    fun doMathOperation(mathOperation: MathOperation) {
        when (_state.value) {
            STATE_FIRST -> {
                _operation.value = mathOperation
                if (_currentNumber.value.isNotEmpty()) {
                    firstNumber = _currentNumber.value.toDouble()
                    _currentNumber.value = df.format(firstNumber)
                    _state.value = STATE_OPERATION
                }
            }
            STATE_OPERATION -> {
                _operation.value = mathOperation
            }
            STATE_SECOND -> {
                if (_currentNumber.value.isNotEmpty()) {
                    secondNumber = _currentNumber.value.toDouble()
                    if (!calculate()) {
                        _currentNumber.value = ""
                        return
                    }
                    _operation.value = mathOperation
                    _state.value = STATE_OPERATION
                }
            }
            else -> {}
        }
    }

    private fun clear() {
        _currentNumber.value = ""
        isComma = false
    }

    private fun allClear() {
        clear()
        firstNumber = 0.0
        secondNumber = 0.0
        _state.value = STATE_FIRST
    }

    private fun calculate(): Boolean {
        if (_operation.value == DIV && secondNumber == 0.0) {
            _state.value = ERROR
            return false
        }
        firstNumber = when (_operation.value) {
            PLUS -> firstNumber + secondNumber
            MINUS -> firstNumber - secondNumber
            MULT -> firstNumber * secondNumber
            DIV -> firstNumber / secondNumber
            else -> 0.0
        }
        _currentNumber.value = df.format(firstNumber)
        return true
    }

    private fun calculateWithPercent(): Boolean {
        if (_operation.value == DIV && secondNumber == 0.0) {
            _state.value = ERROR
            return false
        }
        secondNumber /= 100
        firstNumber = when (_operation.value) {
            PLUS -> firstNumber * (1 + secondNumber)
            MINUS -> firstNumber * (1 - secondNumber)
            MULT -> firstNumber * secondNumber
            DIV -> firstNumber / secondNumber
            else -> 0.0
        }
        _currentNumber.value = df.format(firstNumber)
        return true
    }
}