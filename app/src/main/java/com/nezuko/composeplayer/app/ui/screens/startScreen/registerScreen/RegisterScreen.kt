package com.nezuko.composeplayer.app.ui.screens.startScreen.registerScreen

import android.util.Log
import android.widget.Toast
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.ui.theme.Aqua
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme
import org.koin.androidx.compose.koinViewModel
import kotlin.math.log

const val TAG = "LOGIN_SCREEN"

@Preview
@Composable
fun RegisterScreen(
    onNavigateToRegisterProfileScreen: () -> Unit = {},
    registerViewModel: RegisterViewModel = getRegisterViewModel()
) {
    val context = LocalContext.current
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
                    label = { Text("Почта") }
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
                    label = { Text("Пароль") },
                    visualTransformation = PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(15.dp))

                Button(
                    onClick = {

                        if (passwordText.length < 6) {
                            Toast.makeText(
                                context,
                                "пароль должен быть больше пяти",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Button
                        }

                        registerViewModel.isEmailCorrect(
                            email = loginText,
                            onEmailCheckedComplete = {
                                registerViewModel.updateEmail(loginText)
                                registerViewModel.updatePassword(passwordText)
                                onNavigateToRegisterProfileScreen()
                            },
                            onFailure = {
                                Toast.makeText(context, "почта занята", Toast.LENGTH_SHORT).show()
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Aqua),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text(text = "регистрация", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "Или", color = Color.Black)

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    painter = painterResource(id = R.drawable.sign_up),
                    contentDescription = "",
                    modifier = Modifier.clickable {
                        Log.i("LOGIN_SCREEN", "LoginScreen: google")
                    }
                )
            }
        }
    }
}