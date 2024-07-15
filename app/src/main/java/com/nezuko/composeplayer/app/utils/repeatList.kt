package com.nezuko.composeplayer.app.utils

fun <T> repeatList(list: ArrayList<T>, times: Int): ArrayList<T> {
    return ArrayList(List(times) { list }.flatMap { it })
}