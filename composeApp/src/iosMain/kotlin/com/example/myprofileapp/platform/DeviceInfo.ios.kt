package com.example.myprofileapp.platform

actual class DeviceInfo {
    actual fun getDeviceName(): String = "iOS Simulator"
    actual fun getOsVersion(): String = "iOS 17.0"
}