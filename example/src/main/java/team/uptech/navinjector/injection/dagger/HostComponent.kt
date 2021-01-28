package team.uptech.navinjector.injection.dagger

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.Subcomponent
import team.uptech.dagger_vm_processor.ViewModelModule
import team.uptech.navinjector.CoordinatorImpl
import team.uptech.navinjector.HostCoordinator
import team.uptech.navinjector.first.FirstViewModel
import team.uptech.navinjector.second.SecondViewModel
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Host activity scoped component
 */
@Subcomponent(
    modules = [HostModule::class]
)
@HostScope
abstract class HostComponent {
    abstract val subgraphComponentFactory: SubgraphComponent.Factory

    @Host
    abstract fun viewModelFactory(): ViewModelProvider.Factory

    abstract fun hostCoordinator(): CoordinatorImpl

    @Subcomponent.Factory
    interface Factory {
        fun create(): HostComponent
    }

    companion object INSTANCE {
        @Volatile
        private var instance: HostComponent? = null

        operator fun invoke(parentComponent: AppComponent): HostComponent {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = parentComponent.hostComponentFactory.create()
                    }
                }
            }
            return instance!!
        }

        fun kill() {
            instance = null
            SubgraphComponent.kill()
        }
    }
}


@Module(
    includes = [Host_ViewModelModule::class]
)
abstract class HostModule {

    @Binds
    abstract fun bindCoordinator(coordinatorImpl: CoordinatorImpl) : HostCoordinator

    companion object {
        @Provides
        @HostScope
        fun provideCoordinator() = CoordinatorImpl()

    }



}

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class HostScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@ViewModelModule(
    viewModels = [FirstViewModel::class, SecondViewModel::class],
    scope = HostScope::class
)
annotation class Host