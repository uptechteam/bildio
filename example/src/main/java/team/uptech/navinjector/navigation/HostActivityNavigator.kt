package team.uptech.navinjector.navigation

import android.os.Bundle
import androidx.navigation.NavController
import team.uptech.bildio.navigation.NavControllerNavigator
import team.uptech.bildio.navigation.NavControllerRoute
import team.uptech.navinjector.HostActivity
import team.uptech.navinjector.R
import java.lang.ref.Reference

class HostActivityNavigator(
    navController: NavController,
    activity: Reference<HostActivity>
) : NavControllerNavigator<Route, Route.Destination>(navController) {

    override val currentDestination: Route.Destination?
        get() = navController.currentDestination?.let {
            Route.Destination(it.id)
        }
}

//TODO generate this
sealed class Route(
    navigateId: Int,
    bundle: Bundle? = null
) : NavControllerRoute<Route.Destination>(navigateId, bundle) {

    open class Destination(
        destinationId: Int
    ) : NavControllerDestination(destinationId)

    class Second(
        destinationId: Int,
        bundle: Bundle? = null
    ) : Route(destinationId, bundle) {
        override val destination: Destination
            get() = Destination(R.id.secondFragment)

        companion object {
            fun fromFirst() = Second(
                R.id.action_firstFragment_to_secondFragment
            )
        }
    }

    class Sub(
        destinationId: Int,
        bundle: Bundle? = null
    ) : Route(destinationId, bundle) {
        override val destination: Destination
            get() = Destination(R.id.subgraphFragment)

        companion object {
            fun fromSecond() = Sub(
                R.id.action_secondFragment_to_subgraph
            )
        }
    }

}