package com.example.myprofileapp

import android.app.Application
import com.example.myprofileapp.di.androidModule
import com.example.myprofileapp.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MainApplication)
            modules(sharedModule, androidModule)
        }
    }
}