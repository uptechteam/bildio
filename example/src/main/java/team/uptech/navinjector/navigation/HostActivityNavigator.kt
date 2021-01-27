package team.uptech.navinjector.navigation

import android.os.Bundle
import androidx.navigation.NavController
import team.uptech.bildio.navigation.NavControllerNavigator
import team.uptech.bildio.navigation.NavControllerRoute

class HostActivityNavigator(
    navController: NavController
) : NavControllerNavigator<Route, Route.Destination>(navController) {

}

sealed class Route(
    destinationId: Int,
    bundle: Bundle? = null
) : NavControllerRoute<Route.Destination>(destinationId, bundle) {

    open class Destination(
        destinationId: Int
    ) : NavControllerDestination(destinationId)

}