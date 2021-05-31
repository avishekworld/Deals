package life.avishekworld.test_common

import android.app.Application
import com.target.targetcasestudy.di.presentationModule
import life.avishekworld.data.di.dataModule
import life.avishekworld.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

@ExperimentalStdlibApi
class MockkApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MockkApp)
            loadKoinModules(listOf(presentationModule, domainModule, dataModule))
        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}