package com.nezuko.composeplayer.app.utils

import android.content.ClipDescription
import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.util.CoilUtils
import com.nezuko.domain.model.Resource
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.util.concurrent.TimeUnit


private val TAG = "CUSTOM_IMAGE_LOADER"

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
            .crossfade(crossfade)
            .data(imageUrl)
            .build(),
        contentDescription = contentDescription,
        modifier = modifier,
        imageLoader = createImageLoader(context).build(),
        error = painterResource(id = errorImageResource),
        onSuccess = {
            Log.i(TAG, "ImageFromFirebaseStorage: ${it.result.dataSource}")
        }
        )


}

// Создание кастомного ImageLoader с использованием OkHttpClient
fun createImageLoader(
    context: Context
): ImageLoader.Builder {
    return ImageLoader.Builder(context)
        .respectCacheHeaders(enable = false)
}

fun customImageLoader(context: Context): ImageLoader.Builder {
    val cacheSize = 100 * 1024 * 1024L // 100 MB кэша
    val cacheDir = File(context.cacheDir, "image_cache")
    val cache = Cache(cacheDir, cacheSize)


    val okHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor { chain ->
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()

//            requestBuilder.header("Cache-Control", "only-if-cached")
            requestBuilder.cacheControl(
                CacheControl.Builder()
                    .onlyIfCached()
                    .build()
            )


            val request = requestBuilder.build()
            chain.proceed(request)
        }
        .build()

    return ImageLoader.Builder(context)
//        .memoryCachePolicy(CachePolicy.ENABLED)
//        .memoryCache { MemoryCache.Builder(context).maxSizePercent(0.25).build() }
        .okHttpClient { okHttpClient }
        .respectCacheHeaders(enable = false)
}


fun isImageCached(url: String, okHttpClient: OkHttpClient): Boolean {
    val request = Request.Builder()
        .url(url)
        .cacheControl(
            CacheControl.Builder()
                .onlyIfCached() // Заставляет использовать только кэш
                .maxStale(Int.MAX_VALUE, TimeUnit.SECONDS) // Использовать кэш даже если он устарел
                .build()
        )
        .build()

    return try {
        val response: Response = okHttpClient.newCall(request).execute()
        response.isSuccessful && response.body != null // Проверка успешности ответа и наличия данных
    } catch (e: Exception) {
        false // В случае ошибки считаем, что изображение не закэшировано
    }
}

//@Composable
//fun customImageLoader(context: Context) =
//    ImageLoader.Builder(LocalContext.current)
//        .diskCache {
//            DiskCache.Builder()
//                .directory(context.cacheDir.resolve("image_cache"))
//                .maxSizeBytes(100 * 1024 * 1024)
//                .build()
//        }
//
//        .memoryCache {
//            MemoryCache.Builder(context)
//                .maxSizePercent(0.25)
//                .build()
//        }

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

