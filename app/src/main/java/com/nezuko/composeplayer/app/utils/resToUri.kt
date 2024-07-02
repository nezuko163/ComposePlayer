package com.nezuko.composeplayer.app.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri

fun Context.resToUri(resId: Int) = with(this) {
    Uri.Builder()
        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(resources.getResourcePackageName(resId))
        .appendPath(resources.getResourceTypeName(resId))
        .appendPath(resources.getResourceEntryName(resId))
        .build()
}