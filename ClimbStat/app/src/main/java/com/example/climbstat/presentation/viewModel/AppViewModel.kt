package com.example.climbstat.presentation.viewModel

data class AppViewModel (
    val authViewModel: AuthViewModel,
    val climbingGymsViewModel: ClimbingGymViewModel,
    val climbingGymDetailViewModel: ClimbingGymDetailViewModel,
    val boulderViewModel: BoulderViewModel,
    val boulderDetailsViewModel: BoulderDetailsViewModel,
    val profileViewModel: ProfileViewModel,
    val topoViewModel: TopoViewModel
)