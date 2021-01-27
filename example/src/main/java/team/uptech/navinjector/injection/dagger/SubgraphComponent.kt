package team.uptech.navinjector.injection.dagger

import androidx.lifecycle.ViewModelProvider
import dagger.Module
import dagger.Subcomponent
import team.uptech.dagger_vm_processor.ViewModelModule
import team.uptech.navinjector.other.SubgraphViewModel
import javax.inject.Qualifier
import javax.inject.Scope

@Subcomponent(
    modules = [SubgraphModule::class]
)
@SubgraphScope
abstract class SubgraphComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SubgraphComponent
    }

    @Subgraph
    abstract fun viewModelFactory(): ViewModelProvider.Factory

    companion object INSTANCE {
        @Volatile
        private var instance: SubgraphComponent? = null

        operator fun invoke(parentComponent: HostComponent): SubgraphComponent {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = parentComponent.subgraphComponentFactory.create()
                    }
                }
            }
            return instance!!
        }

        fun kill() {
            instance = null
        }
    }
}


@Module(
    includes = [
        Subgraph_ViewModelModule::class
    ]
)
abstract class SubgraphModule {

}

@Scope
@Retention(AnnotationRetention.SOURCE)
annotation class SubgraphScope

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
@ViewModelModule(
    viewModels = [SubgraphViewModel::class],
    scope = SubgraphScope::class
)
annotation class Subgraph