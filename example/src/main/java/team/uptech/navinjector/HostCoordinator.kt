package team.uptech.navinjector

import team.uptech.navinjector.navigation.HostActivityNavigator
import team.uptech.navinjector.navigation.Route

interface HostCoordinator {
    fun goto(route: Route)
}

class CoordinatorImpl : HostCoordinator {
    private lateinit var navigator: HostActivityNavigator

    fun setNavigator(navigator: HostActivityNavigator) {
        this.navigator = navigator
    }

    override fun goto(route: Route) {
        val destination = route.destination
        navigator.navigate(route)
    }
}