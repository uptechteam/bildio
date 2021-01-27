package team.uptech.bildio.di

import androidx.navigation.NavController

interface ControllerProvider : Injectable {
  val navController: NavController
}