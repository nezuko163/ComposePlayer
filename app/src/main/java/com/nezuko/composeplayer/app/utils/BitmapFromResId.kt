package com.nezuko.composeplayer.app.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun bitmapFromResId(context: Context, resId: Int): Bitmap? {
    return BitmapFactory.decodeResource(context.resources, resId)
}