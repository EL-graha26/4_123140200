package com.example.myprofileapp.platform

import kotlinx.coroutines.flow.Flow

expect class BatteryInfo {
    fun observeBatteryLevel(): Flow<Int>
    fun observeChargingStatus(): Flow<Boolean>
}