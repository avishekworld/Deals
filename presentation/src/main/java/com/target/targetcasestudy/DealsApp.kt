package com.target.targetcasestudy

import android.app.Application
import androidx.emoji.bundled.BundledEmojiCompatConfig
import androidx.emoji.text.EmojiCompat
import com.target.targetcasestudy.di.presentationModule
import life.avishekworld.data.di.dataModule
import life.avishekworld.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin


@ExperimentalStdlibApi
class DealsApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@DealsApp)
            loadKoinModules(listOf(presentationModule, domainModule, dataModule))
        }
        EmojiCompat.init(BundledEmojiCompatConfig(this))
    }
}