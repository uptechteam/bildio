package team.uptech.navinjector

import android.app.Application
import team.uptech.bildio.di.Injectable
import team.uptech.navinjector.injection.AppInjector

class App : Application(), Injectable {

    override fun onCreate() {
        super.onCreate()
        AppInjector(this)
    }
}