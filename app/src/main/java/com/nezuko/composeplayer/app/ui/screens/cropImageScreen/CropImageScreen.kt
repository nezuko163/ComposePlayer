package com.nezuko.composeplayer.app.ui.screens.cropImageScreen

import android.content.Context
import android.graphics.Bitmap
import android.graphics.RectF
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Updater
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import coil.request.ImageRequest
import coil.size.Size
import com.nezuko.composeplayer.app.ui.screens.searchScreen.AsdViewModel
import com.nezuko.composeplayer.app.ui.screens.searchScreen.getAsdViewModel
import com.nezuko.composeplayer.ui.theme.Aqua
import java.io.File
import java.io.FileOutputStream

private val TAG = "CROP_IMAGE_SCREEN"

@Composable
fun CropImage(
    modifier: Modifier = Modifier,
    imageUri: Uri,
    onBackPressed: () -> Unit,
    vm: AsdViewModel = getAsdViewModel()
) {
    val onCropComplete = { uri: Uri ->
        vm.updateImage(uri)
        onBackPressed()
    }

    CropImageScreen(
        modifier = modifier,
        imageUri = imageUri,
        onCropComplete = onCropComplete,
        onBackPressed = onBackPressed,
    )
}

@Preview
@Composable
private fun CropImageScreen(
    modifier: Modifier = Modifier,
    onCropComplete: (uri: Uri) -> Unit = {},
    imageUri: Uri = "".toUri(),
    onBackPressed: () -> Unit = {},
) {

    val context = LocalContext.current

    Log.i(TAG, "CropImageScreen: $imageUri")

    var bitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var radius by remember { mutableStateOf(0) }

    LaunchedEffect(imageUri) {
        val request = ImageRequest.Builder(context)
            .data(imageUri)
            .size(Size.ORIGINAL) // Загружаем оригинальный размер изображения
            .build()
        val result = coil.ImageLoader(context).execute(request)
        bitmap = result.drawable?.toBitmap()?.asImageBitmap()
    }

    if (bitmap == null) return

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        topBar = {
            Row(
                modifier = Modifier
                    .background(Color.Black.copy(alpha = 0.6f))
            ) {
                IconButton(
                    onClick = { onBackPressed() },
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        tint = Color.White,
                        contentDescription = "назад",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.Start)
                    )
                }
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        saveBitmap(
                            name = "${imageUri.lastPathSegment}",
                            bitmap = bitmap!!.asAndroidBitmap(),
                            scale = scale,
                            offset = offset,
                            radius = radius,
                            context = context,
                            onCropComplete = onCropComplete
                        )
                    },
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        tint = Color.White,
                        contentDescription = "готово",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.End)
                    )
                }
            }
        }
    ) {
        Canvas(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTransformGestures { _, pan, zoom, _ ->
                        scale *= zoom
                        offset += pan
                    }
                }) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Центрирование и масштабирование изображения
            val centerX = canvasWidth / 2
            val centerY = canvasHeight / 2

            translate(left = offset.x + centerX, top = offset.y + centerY) {
                scale(scale, scale) {
                    drawImage(
                        image = bitmap!!,
                        topLeft = Offset(-bitmap!!.width / 2f, -bitmap!!.height / 2f)
                    )
                }
            }

            // Создаем круглый путь для маски
            val cropRadius = size.minDimension / 3
            radius = cropRadius.toInt()
            val path = Path().apply {
                addOval(
                    Rect(
                        centerX - cropRadius,
                        centerY - cropRadius,
                        centerX + cropRadius,
                        centerY + cropRadius
                    )
                )
            }

            // Наложение маски для обрезки
            clipPath(path, clipOp = ClipOp.Intersect) {
                drawRect(
                    color = Color.Transparent,
                    size = size
                )
            }
            // Рисуем затемненный фон
            drawRect(
                color = Color.Black.copy(alpha = 0.6f),
                size = size
            )


            drawCircle(
                color = Color.White,
                radius = cropRadius,
                center = Offset(centerX, centerY),
                style = Stroke(width = 4.dp.toPx())
            )
        }
    }
}

fun saveBitmap(
    name: String,
    bitmap: Bitmap,
    scale: Float,
    offset: Offset,
    radius: Int,
    context: Context,
    onCropComplete: (Uri) -> Unit,
) {
    val croppedBitmap = Bitmap.createScaledBitmap(
        cropBitmap(bitmap, scale, offset, radius),
        100,
        100,
        true
    )
    val file = File(context.cacheDir, "cropped_$name.png")
    val outputStream = FileOutputStream(file)
    croppedBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    onCropComplete(Uri.fromFile(file))
}

fun cropBitmap(
    bitmap: Bitmap,
    scale: Float,
    offset: Offset,
    radius: Int
): Bitmap {
    val size = radius * 2
    val scaledBitmap = Bitmap.createScaledBitmap(
        bitmap,
        (bitmap.width * scale).toInt(),
        (bitmap.height * scale).toInt(),
        true
    )

    Log.i(TAG, "cropBitmap: $size")

    val offsetX = (scaledBitmap.width / 2 - radius - offset.x.toInt()).coerceAtLeast(0)
    val offsetY = (scaledBitmap.height / 2 - radius - offset.y.toInt()).coerceAtLeast(0)

    return Bitmap.createBitmap(scaledBitmap, offsetX, offsetY, size, size)
}