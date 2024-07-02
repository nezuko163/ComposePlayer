package com.nezuko.composeplayer.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import java.io.InputStream

fun getBitmapFromUri(uri: Uri?, context: Context): Bitmap? {
    var inputStream: InputStream? = null
    try {
        inputStream = uri?.let { context.contentResolver.openInputStream(it) }
        return BitmapFactory.decodeStream(inputStream)
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        inputStream?.close()
    }
    return null
}