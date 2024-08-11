package com.nezuko.composeplayer.app.ui.screens.mainScreen

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.getUserViewModel
import org.koin.androidx.compose.koinViewModel

private const val TAG = "MAIN_SCREEN"


@Preview
@Composable
fun MainScreen(modifier: Modifier = Modifier, uvm: UserViewModel = getUserViewModel()) {
    SetFields(modifier = modifier)
    Log.i(TAG, "SetFields: ${uvm.userProfile.value}")

}

@Composable
private fun SetFields(modifier: Modifier, vm: NumbersViewModel = koinViewModel()) {
    val numb1 by vm.numb1.observeAsState()
    val numb2 by vm.numb2.observeAsState()
    val sum by vm.sum.observeAsState()

    Column(modifier = modifier) {
        OutlinedTextField(
            value = if (numb1 != null) numb1.toString() else "",
            onValueChange = {
                Log.i(TAG, "SetFields:numb1 ${vm.numb1.value}")
                vm.setNumb(it, true)
            },
            modifier = Modifier
                .border(Dp(2f), Color.Cyan, RoundedCornerShape(30f)),
//            label = { Text("number 1") },
//            placeholder = { Text("Ведите чесло...", color = Color.Gray) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                autoCorrect = false
            ),
        )
        Spacer(modifier = Modifier.padding(Dp(10f)))
        OutlinedTextField(
            value = if (numb2 == null) "" else numb2.toString(),
            onValueChange = {
                Log.i(TAG, "SetFields: numb 2 ${vm.numb2.value}")
                vm.setNumb(it, false)
            },
            modifier = Modifier
                .border(Dp(2f), Color.Cyan, RoundedCornerShape(30f)),
//            placeholder = { Text("Ведите чесло...", color = Color.Gray) },
//            label = { Text("number 2") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                autoCorrect = false
            ),
        )
        Text(text = "${if (sum == null) "" else sum}".also {
            Log.i(TAG, "SetFields: sum  ${vm.sum.value}")
        }, color = Color.Black)
    }
}