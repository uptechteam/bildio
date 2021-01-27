package team.uptech.bildio.navigation

import android.os.Bundle

abstract class NavControllerRoute<D : NavControllerRoute.NavControllerDestination>(
    private val destinationId: Int,
    open val bundle: Bundle?
) : Navigator.Route<D> {

  open class NavControllerDestination(val id: Int) : Navigator.Destination
}

