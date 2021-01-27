package team.uptech.navinjector.injection.dagger

import dagger.Component
import dagger.Module
import javax.inject.Singleton

/**
 * App scope module
 */
@Component(
    modules = [AppModule::class]
)
@Singleton
abstract class AppComponent {
    abstract val hostComponentFactory: HostComponent.Factory
}

@Module
abstract class AppModule