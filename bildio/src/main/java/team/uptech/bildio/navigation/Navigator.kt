package team.uptech.bildio.navigation

interface Navigator<T, D> where T : Navigator.Route<D>, D : Navigator.Destination {

  fun navigate(route: T)
  fun navigate(route: T, intermediateRoutes: Collection<T>)

  /**
   * Pops backstack if it's possible
   * @return true if stack has been popped, false otherwise
   */
  fun popBackStack(): Boolean
  fun navigateUp(): Boolean

  interface Route<D : Destination> {
    val destination: D
  }

  interface Destination
}