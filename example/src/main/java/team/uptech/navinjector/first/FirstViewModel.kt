package team.uptech.navinjector.first

import team.uptech.navinjector.HostCoordinator
import team.uptech.navinjector.base.BaseViewModel
import team.uptech.navinjector.navigation.Route
import javax.inject.Inject

class FirstViewModel @Inject constructor(
    private val hostCoordinator: HostCoordinator
) : BaseViewModel() {
    fun onButtonClicked() {
        hostCoordinator.goto(Route.Second.fromFirst())
    }
}
