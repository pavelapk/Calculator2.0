package ru.pavelapk.calculator2.ui

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.pavelapk.calculator2.*
import ru.pavelapk.calculator2.R
import ru.pavelapk.calculator2.calculator.CalculatorEngine
import ru.pavelapk.calculator2.calculator.CalculatorState
import ru.pavelapk.calculator2.ui.theme.appNameFontFamily
import ru.pavelapk.calculator2.ui.theme.primaryTextColor

@Composable
fun Calculator(viewModel: CalculatorViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsState()

    Column(Modifier.padding(16.dp)) {
        Text(
            stringResource(R.string.app_name),
            fontSize = 28.sp,
            fontFamily = appNameFontFamily,
            fontWeight = FontWeight.Medium,
            color = primaryTextColor,
            modifier = Modifier.padding(8.dp)
        )
        Screen(
            text = LocalContext.current.stringForDisplay(
                state.screenNumber,
                state.operation,
                state.state
            ),
            modifier = Modifier.padding(8.dp, 16.dp)
        )

        ButtonPad(
            onDigitClick = viewModel::onDigitClick,
            onOperationClick = viewModel::onOperationClick,
            modifier = Modifier
                .fillMaxHeight()
                .padding(bottom = 60.dp)
        )
    }
}


fun Context.stringForDisplay(num: String, operation: String?, state: CalculatorState): String {
    if (state == CalculatorState.ERROR) return getString(R.string.error)

    var result = if (num.length > CalculatorEngine.MAX_LENGTH) {
        getString(R.string.trunc_num, num.take(CalculatorEngine.MAX_LENGTH - 2))
    } else {
        num
    }
    if (operation != null && state == CalculatorState.STATE_OPERATION) {
        result += operation
    }
    return result
}