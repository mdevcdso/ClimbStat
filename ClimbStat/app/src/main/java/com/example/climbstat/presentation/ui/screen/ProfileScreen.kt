package com.example.climbstat.presentation.ui.screen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.utils.TokenManagerUtils

@Composable
fun ProfileScreen(
    navController: NavController
) {
    val context = LocalContext.current
    val tokenManagerUtils = TokenManagerUtils(context = context)
    Button(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        onClick = {
            tokenManagerUtils.clearToken()
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.Home.route) {
                    inclusive = true
                }
            }
        }
    ) {
        Text("Logout")
    }
}