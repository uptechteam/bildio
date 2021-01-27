package team.uptech.bildio.navigation

import androidx.navigation.NavController
import androidx.navigation.NavOptions

open class NavControllerNavigator<R : NavControllerRoute<D>, D : NavControllerRoute.NavControllerDestination>(
    private val navController: NavController
) : Navigator<R, D> {

  override fun navigate(route: R) {
    navigate(route, listOf())
  }

  override fun navigate(route: R, intermediateRoutes: Collection<R>) {
    try {
      val options =
        navController.currentDestination?.getAction(route.destination.id)?.navOptions
          ?: NavOptions.Builder().build()

      navController.navigate(
          route.destination.id,
          route.bundle,
          options
      )
    } catch (ex: Exception) {
      //TODO do log
    }
  }

  override fun popBackStack(): Boolean {
    return navController.popBackStack()
  }

  override fun navigateUp(): Boolean {
    return navController.navigateUp()
  }

  //Calling this prevents from executing default activity behavior if there is no enabled callbacks
//  private fun callOnBackPressedDispatcher(): Boolean {
//    val dispatcher = activity.onBackPressedDispatcher
//    if (dispatcher.hasEnabledCallbacks()) {
//      dispatcher.onBackPressed()
//      return true
//    }
//    return false
//  }

}