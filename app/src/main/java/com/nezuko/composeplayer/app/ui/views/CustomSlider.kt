package com.nezuko.composeplayer.app.ui.views

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableFloatState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.nezuko.composeplayer.app.ui.viewmodels.PlayerServiceViewModel
import com.nezuko.composeplayer.app.utils.getGlobalViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.time.Duration

private val TAG = "CUSTOM_SLIDER"

@SuppressLint("ReturnFromAwaitPointerEventScope")
@Composable
fun CustomSlider(
    position: Long = 1,
    duration: Long = 1,
    onValueChanged: (Float) -> Unit = {},
    onValueFinishedChanged: (Float) -> Unit = {},
) {
    var sliderPos by remember { mutableFloatStateOf(position.toFloat()) }
    var isSliding by remember { mutableStateOf(false) }

    if (!isSliding) sliderPos = position.toFloat()

    Slider(
        value = sliderPos,
        onValueChange = { value ->
            if (!isSliding) isSliding = true
            sliderPos = value
            onValueChanged(sliderPos)
        },
        onValueChangeFinished = {
            isSliding = false
            onValueFinishedChanged(sliderPos)
        },
        valueRange = 1f..duration.toFloat(),
    )
}