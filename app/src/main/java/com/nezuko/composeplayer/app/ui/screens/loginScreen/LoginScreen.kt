package com.nezuko.composeplayer.app.ui.screens.loginScreen

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nezuko.composeplayer.R
import com.nezuko.composeplayer.app.ui.viewmodels.UserViewModel
import com.nezuko.composeplayer.ui.theme.Aqua
import com.nezuko.composeplayer.ui.theme.ComposePlayerTheme
import org.koin.androidx.compose.koinViewModel

const val TAG = "LOGIN_SCREEN"

@Preview
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = koinViewModel(),
    userViewModel: UserViewModel = koinViewModel()
) {
    var loginText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("52") }
    val context = LocalContext.current


    ComposePlayerTheme {
        Surface(
            modifier = modifier
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
                        authViewModel.registerViaPasswordAndEmail(loginText, passwordText) {
                            it?.let {
                                Log.i("LOGIN_SCREEN", "LoginScreen: $it")
                                userViewModel.setUID(it)
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Aqua),
                    shape = RoundedCornerShape(100.dp)
                ) {
                    Text(text = "login", color = Color.White)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(text = "or", color = Color.Black)

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