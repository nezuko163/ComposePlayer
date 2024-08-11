package com.nezuko.composeplayer.app.ui.screens.startScreen.loginScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.app.ui.viewmodels.getUserViewModel
import com.nezuko.composeplayer.ui.theme.Aqua
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme
import org.koin.androidx.compose.koinViewModel

@Preview
@Composable
fun LoginScreen(
    onAuthComplete: (String) -> Unit = {},
    loginViewModel: LoginViewModel = koinViewModel(),
    userViewModel: UserViewModel = getUserViewModel()
) {
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }

    ComposePlayerTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = if (loginText != "") loginText else "",
                    onValueChange = {
                        loginText = it
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    label = { Text("Email") }
                )

                Spacer(modifier = Modifier.height(5.dp))

                OutlinedTextField(
                    value = if (passwordText != "") passwordText else "",
                    onValueChange = {
                        passwordText = it
                    },
                    keyboardOptions = KeyboardOptions(
                        autoCorrect = true,
                        keyboardType = KeyboardType.Email
                    ),
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {
                        loginViewModel.loginViaEmailAndPassword(loginText, passwordText) {
                            if (it.isNotEmpty()) {
                                onAuthComplete.invoke(it)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Aqua),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text(text = "войти", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "or", color = Color.Black)

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.sign_in),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        Log.i("LOGIN_SCREEN", "LoginScreen: google")
                    }
                )
            }
        }
    }
}