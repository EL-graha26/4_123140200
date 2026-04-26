package com.example.myprofileapp.platform

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

actual class BatteryInfo {
    actual fun observeBatteryLevel(): Flow<Int> = flowOf(100)
    actual fun observeChargingStatus(): Flow<Boolean> = flowOf(false)
}