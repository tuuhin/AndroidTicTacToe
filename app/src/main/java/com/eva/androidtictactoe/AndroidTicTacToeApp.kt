package com.eva.androidtictactoe

import android.app.Application
import com.eva.androidtictactoe.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AndroidTicTacToeApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@AndroidTicTacToeApp)
            modules(appModule)
        }
    }
}