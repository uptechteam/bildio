package team.uptech.navinjector

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavGraph
import team.uptech.bildio.di.ControllerProvider
import team.uptech.bildio.di.NavControllerInjector
import team.uptech.bildio.di.RetainedState
import team.uptech.bildio.navigation.Navigator
import team.uptech.navinjector.injection.dagger.AppComponent
import team.uptech.navinjector.injection.dagger.HostComponent
import team.uptech.navinjector.injection.dagger.SubgraphComponent
import team.uptech.navinjector.navigation.HostActivityNavigator
import java.lang.ref.Reference

class HostActivityInjector(
    private val appComponent: AppComponent,
    private val activity: Reference<HostActivity>
) : NavControllerInjector(activity) {

    private var currentFactory: ViewModelProvider.Factory? = null
    private var hostComponent: HostComponent? = null
    private var subgraphComponent: SubgraphComponent? = null
    private lateinit var navigator : HostActivityNavigator

    override fun onSubGraph(navGraph: NavGraph): DestinationLifecycleObserver? =
        when (navGraph.id) {
            R.id.nav_graph -> DestinationLifecycleObserver(navGraph.id, {
                hostComponent = HostComponent(appComponent).also {
                    it.hostCoordinator().setNavigator(navigator)
                }
                currentFactory = hostComponent?.viewModelFactory()
            }, {
                if (configurationChange()) {
                    HostComponent.kill()
                    currentFactory = null
                }
            })

            R.id.subgraph -> DestinationLifecycleObserver(navGraph.id, {
                subgraphComponent = SubgraphComponent(
                    hostComponent ?: throw Exception("Host component not initialized")
                )
                currentFactory = subgraphComponent?.viewModelFactory()
            }, {
                if (configurationChange()) {
                    SubgraphComponent.kill()
                    currentFactory = hostComponent?.viewModelFactory()
                }
            })
            else -> null
        }

    override fun getVMFactory(fragment: Fragment): ViewModelProvider.Factory = currentFactory!!

    override fun saveState(): RetainedState = HostState(
        hostComponent,
        subgraphComponent,
        currentFactory
    )

    override fun restoreState(state: RetainedState) {
        (state as? HostState)?.let {
            hostComponent = it.hostComponent
            subgraphComponent = it.subgraphComponent
            currentFactory = it.factory
        }
    }

    inner class HostState(
        val hostComponent: HostComponent? = null,
        val subgraphComponent: SubgraphComponent? = null,
        val factory: ViewModelProvider.Factory? = null
    ) : RetainedState

    fun configurationChange() = activity.get()?.isChangingConfigurations == false

    override fun inject(injectable: ControllerProvider) {
        super.inject(injectable)
        navigator = HostActivityNavigator(injectable.navController, activity)
        injectable.navController.setGraph(R.navigation.nav_graph)
    }
}