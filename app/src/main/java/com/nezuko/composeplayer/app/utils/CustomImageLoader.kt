package com.nezuko.composeplayer.app.utils

import android.content.ClipDescription
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.ImageRequest
import com.nezuko.domain.model.Resource


@Composable
fun ImageFromFirebaseStorage(
    modifier: Modifier = Modifier,
    imageUrl: String,
    errorImageResource: Int,
    contentDescription: String = "",
    crossfade: Boolean = false,
    ) {
    val context = LocalContext.current
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(imageUrl)
            .crossfade(crossfade)
            .build(),
        error = painterResource(id = errorImageResource),
        imageLoader = customImageLoader(context).build(),
        contentDescription = contentDescription,
        modifier = modifier,
    )
}

@Composable
fun customImageLoader(context: Context) =
    ImageLoader.Builder(LocalContext.current)
        .diskCache {
            DiskCache.Builder()
                .directory(context.cacheDir.resolve("image_cache"))
                .maxSizeBytes(100 * 1024 * 1024)
                .build()
        }

        .memoryCache {
            MemoryCache.Builder(context)
                .maxSizePercent(0.25)
                .build()
        }

fun clearCache(imageLoader: ImageLoader) {
    clearMemoryCache(imageLoader)
    clearDiskCache(imageLoader)
}

fun clearMemoryCache(imageLoader: ImageLoader) {
    (imageLoader.memoryCache as MemoryCache).clear()

}

@OptIn(ExperimentalCoilApi::class)
fun clearDiskCache(imageLoader: ImageLoader) {
    (imageLoader.diskCache as DiskCache).clear()
}

