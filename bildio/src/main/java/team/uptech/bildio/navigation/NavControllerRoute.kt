package team.uptech.bildio.navigation

import android.os.Bundle

abstract class NavControllerRoute<D : NavControllerRoute.NavControllerDestination>(
    val navigateId: Int, //might be either Navigation Action/Destination (Fragment id)
    open val bundle: Bundle?
) : Navigator.Route<D> {

    open class NavControllerDestination(val id: Int) : Navigator.Destination
}

