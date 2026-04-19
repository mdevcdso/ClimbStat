package com.example.climbstat.presentation.ui.screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.climbstat.domain.usecase.state.AuthUiState
import com.example.climbstat.presentation.ui.navigation.Screen
import com.example.climbstat.presentation.viewModel.AuthViewModel
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    navController: NavController
) {
    val context = LocalContext.current
    val authStateUi = viewModel.authStateUi.collectAsState()

    var userName by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isNameValid = userName.isNotBlank()
    val isEmailValid = userEmail.isNotBlank() && userEmail.contains("@")
    val isPasswordValid = userPassword.length >= 6
    val isFormValid = isNameValid && isEmailValid && isPasswordValid

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (authStateUi.value) {
            is AuthUiState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.size(40.dp))
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, "Erreur lors de l'inscription", Toast.LENGTH_LONG).show()
                viewModel.resetUiState()
            }
            is AuthUiState.Success -> {
                navController.navigate(Screen.Profile.route) {
                    popUpTo(Screen.Register.route) { inclusive = true }
                    popUpTo(Screen.Login.route) { inclusive = true }
                }
                viewModel.resetUiState()
            }
            is AuthUiState.Initial -> {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Bienvenue sur ClimbStat+ !!",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Leader mondial dans le suivi de performances en escalade",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Pour commencer, inscrivez-vous",
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OutlinedTextField(
                            value = userName,
                            onValueChange = { userName = it },
                            label = {
                                Text(
                                    text = "Nom",
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            singleLine = true,
                            isError = userName.isNotEmpty() && !isNameValid,
                            supportingText = {
                                if (userName.isNotEmpty() && !isNameValid) {
                                    Text("Le nom est obligatoire")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                        OutlinedTextField(
                            value = userEmail,
                            onValueChange = { userEmail = it },
                            label = {
                                Text(
                                    text = "Email",
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            singleLine = true,
                            isError = userEmail.isNotEmpty() && !isEmailValid,
                            supportingText = {
                                if (userEmail.isNotEmpty() && !isEmailValid) {
                                    Text("Email invalide")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                        OutlinedTextField(
                            value = userPassword,
                            onValueChange = { userPassword = it },
                            label = {
                                Text(
                                    text = "Mot de passe",
                                    modifier = Modifier.fillMaxWidth(),
                                    style = MaterialTheme.typography.bodySmall
                                )
                            },
                            singleLine = true,
                            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                            isError = userPassword.isNotEmpty() && !isPasswordValid,
                            supportingText = {
                                if (userPassword.isNotEmpty() && !isPasswordValid) {
                                    Text("Au moins 6 caractères")
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Text(
                                text = if (passwordVisible) "Masquer" else "Afficher",
                                modifier = Modifier
                                    .padding(top = 4.dp, end = 4.dp)
                                    .clickable { passwordVisible = !passwordVisible },
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            enabled = isFormValid,
                            onClick = {
                                viewModel.register(userName, userEmail, userPassword)
                            }
                        ) {
                            Text(
                                text = "S'inscrire",
                                fontSize = 20.sp,
                                color = MaterialTheme.colorScheme.tertiary,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
                        Row {
                            Text(
                                text = "Déjà un compte ? ",
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Connectez-vous !",
                                fontSize = 18.sp,
                                modifier = Modifier
                                    .padding(top = 16.dp)
                                    .clickable {
                                        navController.navigate(Screen.Login.route) {
                                            popUpTo(Screen.Register.route) { inclusive = true }
                                        }
                                    },
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
}
