package com.example.myprofileapp.platform

actual class DeviceInfo {
    actual fun getDeviceName(): String = android.os.Build.MODEL
    actual fun getOsVersion(): String = "Android ${android.os.Build.VERSION.RELEASE}"
}