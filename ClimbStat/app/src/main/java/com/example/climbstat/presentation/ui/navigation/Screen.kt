package com.example.climbstat.presentation.ui.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.climbstat.R

sealed class Screen(
    val route: String,
    val label: String,
    val icon: Int
) {
    object Login: Screen(
        route = "login",
        label = "Login",
        icon = R.drawable.ic_launcher_foreground
    )
    object Register: Screen(
        route = "register",
        label = "Register",
        icon = R.drawable.ic_launcher_foreground
    )
    object Home: Screen(
        route = "home",
        label = "Home",
        icon = R.drawable.home_svgrepo_com
    )
    object Profile: Screen(
        route = "profile",
        label = "Profile",
        icon = R.drawable.profile_svgrepo_com
    )
    object Gym: Screen(
        route = "gym",
        label = "Salle",
        icon = R.drawable.buildings_svgrepo_com
    )
    object GymDetail: Screen(
        route = "gymDetail/{gymId}",
        label = "Salle",
        icon = R.drawable.buildings_svgrepo_com
    ){
        fun createRoute(gymId: String) = "gymDetail/$gymId"
    }
    object Boulder: Screen(
        route = "boulder/{gymId}",
        label = "Blocs",
        icon = R.drawable.picture_frame_svgrepo_com
    ){
        fun createRoute(gymId: String?) = "boulder/$gymId"
    }
    object BoulderDetail: Screen(
        route = "boulderDetail/{gymId}/{boulderIndex}",
        label = "Blocs",
        icon = R.drawable.picture_frame_svgrepo_com
    ){
        fun createRoute(gymId: String, boulderIndex: Int) = "boulderDetail/$gymId/$boulderIndex"
    }
    object Topo: Screen(
        route = "topo",
        label = "Topo",
        icon = R.drawable.goal_mountain_flag_achievement_svgrepo_com
    )

}