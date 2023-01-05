package org.includejoe.instagramclone.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.includejoe.instagramclone.R
import org.includejoe.instagramclone.presentation.components.Toast
import org.includejoe.instagramclone.util.Response
import org.includejoe.instagramclone.util.Screens


@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: AuthenticationViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val emailState = remember {
            mutableStateOf("")
        }
        val passwordState = remember {
            mutableStateOf("")
        }
        Image(
            painter = painterResource(id = R.drawable.ic_logo_text_black),
            contentDescription = "logo",
            modifier = Modifier
                .padding(8.dp)
        )

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = {
                Text(text = "Email")
            }
        )

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            label = {
                Text(text = "Password")
            },
            visualTransformation = PasswordVisualTransformation()
        )

        when(val response = viewModel.signInState.value) {
            is Response.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(vertical = 16.dp)
                        .size(25.dp),
                    color = Color.Black
                )
            }
            is Response.Success -> {
                if(response.data){
                    LaunchedEffect(key1 = true) {
                        navController.navigate(Screens.ProfileScreen.route) {
                            popUpTo(Screens.LoginScreen.route) {
                                inclusive = true
                            }
                        }
                    }
                }
//                    else {
//                        Toast(message = "Login failed")
//                    }
            }
            is Response.Error<*> -> {
                Toast(message=response.message)
            }
        }

        Button(
            onClick = {
            viewModel.signIn(
                email = emailState.value,
                password = passwordState.value
            )},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(text="Login")
        }

        Text(
            text = "New user? Sign Up ",
            color = Color.Blue,
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    navController.navigate(route = Screens.SignUpScreen.route) {
                        launchSingleTop = true
                    }
                }
        )
    }
}
