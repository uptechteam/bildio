package team.uptech.navinjector.second

import team.uptech.navinjector.HostCoordinator
import team.uptech.navinjector.base.BaseViewModel
import team.uptech.navinjector.navigation.Route
import javax.inject.Inject

class SecondViewModel @Inject constructor(
    private val coordinator: HostCoordinator
) : BaseViewModel() {
    fun onButton2Clicked() {

    }

    fun onButtonClicked() {
        coordinator.goto(Route.Sub.fromSecond())
    }

}