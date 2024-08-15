package com.nezuko.composeplayer.app.ui.screens.cropImageScreen

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.nezuko.composeplayer.app.ui.screens.profileScreen.ProfileViewModel
import com.nezuko.composeplayer.app.ui.screens.profileScreen.getProfileViewModel
import com.yalantis.ucrop.UCrop
import java.io.File

private val TAG = "CROP_IMAGE_SCREEN"

@Composable
fun CropImage(
    imageUri: Uri,
    onBackPressed: () -> Unit,
    vm: ProfileViewModel = getProfileViewModel()
) {
    val onCropComplete = { uri: Uri ->
        vm.updateImage(uri)
        onBackPressed()
    }

    CropImageScreen(uri = imageUri, onCropComplete = onCropComplete)
}

@Composable
fun CropImageScreen(
    uri: Uri,
    onCropComplete: (uri: Uri) -> Unit
) {
    ImageCropper(uri = uri) { croppedImageUri ->
        onCropComplete(croppedImageUri)
    }
}

@Composable
fun TopBarr(
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit,
    onCropComplete: () -> Unit,
) {

    val context = LocalContext.current

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
            onClick = { onCropComplete() },
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


fun startCrop(
    context: Context,
    uri: Uri,
    activityResultLauncher: ActivityResultLauncher<Intent>
) {
    val uCrop = UCrop.of(uri, Uri.fromFile(File(context.cacheDir, "cropped_${uri.lastPathSegment}.jpg")))
        .withAspectRatio(1f, 1f) // Пример соотношения сторон, можете настроить как нужно
        .withMaxResultSize(200, 200) // Максимальный размер результата
    activityResultLauncher.launch(uCrop.getIntent(context))
}

@Composable
fun ImageCropper(
    uri: Uri,
    onImageCropped: (Uri) -> Unit
) {
    val context = LocalContext.current

    // Создаем ActivityResultLauncher для обработки результата обрезки
    val activityResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultUri = UCrop.getOutput(result.data!!)
                resultUri?.let { onImageCropped(it) }
            }
        }
    )

    // Запускаем UCrop при первом рендере компонента
    LaunchedEffect(uri) {
        startCrop(context, uri, activityResultLauncher)
    }
}