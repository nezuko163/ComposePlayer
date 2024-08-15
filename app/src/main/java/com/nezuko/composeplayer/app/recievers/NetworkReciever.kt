package com.nezuko.composeplayer.app.recievers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.nezuko.domain.usecase.DisableOnlineModeUseCase
import com.nezuko.domain.usecase.EnableOnlineModeUseCase

class NetworkReciever(
    private val enableOnlineModeUseCase: EnableOnlineModeUseCase,
    private val disableOnlineModeUseCase: DisableOnlineModeUseCase
): BroadcastReceiver() {
    override fun onReceive(p0: Context?, p1: Intent?) {
        TODO("Not yet implemented")
    }
}