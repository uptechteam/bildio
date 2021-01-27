package team.uptech.navinjector.injection

import android.app.Activity
import team.uptech.bildio.di.ApplicationInjector
import team.uptech.bildio.di.ControllerProvider
import team.uptech.bildio.di.Injector
import team.uptech.navinjector.App
import team.uptech.navinjector.HostActivity
import team.uptech.navinjector.HostActivityInjector
import team.uptech.navinjector.injection.dagger.DaggerAppComponent
import java.lang.ref.WeakReference

class AppInjector(app: App) : ApplicationInjector<ControllerProvider>(app) {
    private val appComponent = DaggerAppComponent.create()

    override fun createInjectorFor(activity: Activity): Injector<ControllerProvider>? =
        when (activity) {
            is HostActivity -> HostActivityInjector(
                appComponent,
                WeakReference(activity)
            )
            else -> null
        }

}