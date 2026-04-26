package com.example.myprofileapp.di

import com.example.myprofileapp.data.local.NoteRepository
import com.example.myprofileapp.data.local.SettingsManager
import com.example.myprofileapp.viewmodel.NotesViewModel
import com.example.myprofileapp.viewmodel.ProfileViewModel
import org.koin.dsl.module

val sharedModule = module {
    single { NoteRepository(get()) }
    single { SettingsManager(get()) }

    factory { NotesViewModel(get()) }
    factory { ProfileViewModel(get()) }
}